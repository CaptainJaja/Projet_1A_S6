package com.example.formation3;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.AlertDialog; // For AlertDialog.Builder
import android.text.InputType; // For InputType.TYPE_CLASS_NUMBER
import android.widget.EditText; // For EditText

import java.lang.reflect.Method; // For Method (used in pairDevice)



public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    private static final UUID UUID_BT = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 2;
    private static final int REQUEST_BLUETOOTH_CONNECT = 100;

    private ArrayList<String> deviceList = new ArrayList<>();
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView lvDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        // Initialize buttons
        Button btnForward = findViewById(R.id.btnForward);
        Button btnBackward = findViewById(R.id.btnBackward);
        Button btnLeft = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnLED = findViewById(R.id.btnLED);
        Button btnScan = findViewById(R.id.btnScan);

        lvDevices = findViewById(R.id.lvDevices);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        lvDevices.setAdapter(adapter);

        // Set button listeners
        btnForward.setOnClickListener(v -> sendCommand(String.valueOf(0)));
        btnBackward.setOnClickListener(v -> sendCommand(String.valueOf(1)));
        btnLeft.setOnClickListener(v -> sendCommand("L"));
        btnRight.setOnClickListener(v -> sendCommand("R"));
        btnStop.setOnClickListener(v -> sendCommand("S"));
        btnLED.setOnClickListener(v -> sendCommand("A"));

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300); // 5 minutes
        startActivity(discoverableIntent);

        // Scan for devices when button is clicked
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            btnScan.setOnClickListener(v -> scanForDevices());
        }

        // Connect to a device when selected from the list
        lvDevices.setOnItemClickListener((parent, view, position, id) -> {
            BluetoothDevice device = bluetoothDevices.get(position);
            connectToDevice(device);
        });

        // Request Bluetooth and location permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            requestPermissions();
        }

        // code for requestPermissions that I put again to see if it would work even though it's right above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, REQUEST_LOCATION_PERMISSION);
            }
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // Bluetooth is already enabled, start scanning
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                scanForDevices();
            }
        }

    }

    private void sendCommand(String command) {
        if (outputStream == null) {
            Toast.makeText(this, "Not connected to a device", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            outputStream.write(command.getBytes());
        } catch (IOException e) {
            Toast.makeText(this, "Failed to send command", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_LOCATION_PERMISSION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void scanForDevices() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported on this device", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }

        // Ensure permissions are granted before proceeding
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, REQUEST_LOCATION_PERMISSION);

            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Please enable location services for Bluetooth scanning", Toast.LENGTH_LONG).show();
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }


        // Cancel ongoing discovery
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        // Clear previous list
        deviceList.clear();
        bluetoothDevices.clear();
        adapter.notifyDataSetChanged();



        // Register the receiver before discovery to avoid missing devices
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        // Start discovering new devices
        boolean started = bluetoothAdapter.startDiscovery();  // ici ya un prblm
        if (started) {
            Toast.makeText(this, "Scanning for devices...", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to start scanning", Toast.LENGTH_SHORT).show();
        }
    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    if (device != null && device.getName() != null) {
                        if (!deviceList.contains(device.getName() + " - " + device.getAddress())) { // Avoid duplicates
                            bluetoothDevices.add(device);
                            deviceList.add(device.getName() + " - " + device.getAddress());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    };

    private void connectToDevice(BluetoothDevice device) {
        // Check if BLUETOOTH_CONNECT permission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed to show the PIN dialog
            showPinDialog(device);
        } else {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_BLUETOOTH_CONNECT);
        }
    }

    private void showPinDialog(BluetoothDevice device) {
        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter PIN for " + device.getName());

        final EditText pinInput = new EditText(this);
        pinInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(pinInput);

        builder.setPositiveButton("Connect", (dialog, which) -> {
            String pin = pinInput.getText().toString();
            if (!pin.isEmpty()) {
                // Pair with the device using the PIN
                pairDevice(device, pin);
            } else {
                Toast.makeText(this, "Please enter a valid PIN", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void pairDevice(BluetoothDevice device, String pin) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                // Set the PIN for pairing
                Method method = device.getClass().getMethod("setPin", byte[].class);
                method.invoke(device, pin.getBytes());

                // Pair with the device
                Method pairMethod = device.getClass().getMethod("createBond");
                pairMethod.invoke(device);

                // Connect to the device after pairing
                new ConnectTask().execute(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to pair with device", Toast.LENGTH_SHORT).show();
        }
    }

    private class ConnectTask extends AsyncTask<BluetoothDevice, Void, Boolean> {
        @Override
        protected Boolean doInBackground(BluetoothDevice... devices) {
            try {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    bluetoothSocket = devices[0].createInsecureRfcommSocketToServiceRecord(UUID_BT);
                    bluetoothSocket.connect();
                    outputStream = bluetoothSocket.getOutputStream();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Check if BLUETOOTH_CONNECT permission is granted
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, retrieve and display the device name
                    String deviceName = bluetoothSocket.getRemoteDevice().getName();
                    Toast.makeText(MainActivity.this, "Connected to " + deviceName, Toast.LENGTH_SHORT).show();
                } else {
                    // Permission not granted, show a generic message
                    Toast.makeText(MainActivity.this, "Connected to device", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void disconnectFromDevice() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                bluetoothSocket = null;
                outputStream = null;
                Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(this, "Failed to disconnect", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(receiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        disconnectFromDevice();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retry scanning for devices
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    scanForDevices();
                }
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(this, "Bluetooth permission is required to scan for devices", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                // Bluetooth is enabled, start scanning for devices
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    scanForDevices();
                }
            } else {
                // User declined to enable Bluetooth
                Toast.makeText(this, "Bluetooth must be enabled to use this app", Toast.LENGTH_SHORT).show();
            }
        }
    }

}