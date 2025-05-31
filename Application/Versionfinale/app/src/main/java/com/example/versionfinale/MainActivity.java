package com.example.versionfinale;

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
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BluetoothController";
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private OutputStream outputStream;

    private static final UUID UUID_BT = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_PERMISSIONS = 2;
    private static final int REQUEST_ENABLE_LOCATION = 3;

    private ArrayList<String> deviceList = new ArrayList<>();
    private ArrayList<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView lvDevices;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            showToast("Bluetooth not supported");
            finish();
            return;
        }

        checkPermissions();
    }

    private void initUI() {
        ImageButton btnForward = findViewById(R.id.btnForward);
        ImageButton btnBackward = findViewById(R.id.btnBackward);
        ImageButton btnLeft = findViewById(R.id.btnLeft);
        ImageButton btnRight = findViewById(R.id.btnRight);
        ImageButton btnStop = findViewById(R.id.btnStop);
        ImageButton btnSPEED = findViewById(R.id.btnLED);
        ImageButton btnScan = findViewById(R.id.btnScan);

        lvDevices = findViewById(R.id.lvDevices);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceList);
        lvDevices.setAdapter(adapter);

        // Set button listeners
        btnForward.setOnClickListener(v -> sendCommand("z"));
        btnBackward.setOnClickListener(v -> sendCommand("s"));
        btnLeft.setOnClickListener(v -> sendCommand("q"));
        btnRight.setOnClickListener(v -> sendCommand("d"));
        btnStop.setOnClickListener(v -> sendCommand(" "));
        btnSPEED.setOnClickListener(v -> sendCommand("t"));
        btnScan.setOnClickListener(v -> checkPermissions());

        lvDevices.setOnItemClickListener((parent, view, position, id) -> {
            if (position >= 0 && position < bluetoothDevices.size()) {
                BluetoothDevice device = bluetoothDevices.get(position);
                connectToDevice(device);
            }
        });
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            String[] permissions = {
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION
            };

            ArrayList<String> permissionsToRequest = new ArrayList<>();
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsToRequest.add(permission);
                }
            }

            if (!permissionsToRequest.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        permissionsToRequest.toArray(new String[0]),
                        REQUEST_PERMISSIONS);
                return;
            }
        } else {
            // For older versions, we still need location permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_PERMISSIONS);
                return;
            }
        }

        // Check if Bluetooth is enabled
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            checkLocationEnabled();
        }
    }

    private void checkLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            new AlertDialog.Builder(this)
                    .setTitle("Location Required")
                    .setMessage("Please enable location services to scan for Bluetooth devices")
                    .setPositiveButton("Settings", (dialog, which) -> {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            startBluetoothDiscovery();
        }
    }

    private void startBluetoothDiscovery() {

        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        deviceList.clear();
        bluetoothDevices.clear();
        adapter.notifyDataSetChanged();

        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (device != null && hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                        String deviceName = device.getName();
                        String deviceAddress = device.getAddress();
                        if (deviceName != null && !deviceList.contains(deviceName + " - " + deviceAddress)) {
                            bluetoothDevices.add(device);
                            deviceList.add(deviceName + " - " + deviceAddress);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        // Start discovery
        if (bluetoothAdapter.startDiscovery()) {
            showToast("Scanning for devices...");
        } else {
            showToast("Failed to start scanning");
        }
    }

    private void connectToDevice(BluetoothDevice device) {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_PERMISSIONS);
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Connect to " + device.getName())
                .setMessage("Do you want to connect to this device?")
                .setPositiveButton("Connect", (dialog, which) -> {
                    new ConnectTask().execute(device);
                    dialog.dismiss();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendCommand(String command) {
        if (outputStream == null) {
            showToast("Not connected to a device");
            return;
        }

        try {
            outputStream.write(command.getBytes());
            outputStream.flush();
            Log.d(TAG, "Command sent: " + command);
        } catch (IOException e) {
            Log.e(TAG, "Error sending command", e);
            showToast("Failed to send command");
            disconnectFromDevice();
        }
    }

    private class ConnectTask extends AsyncTask<BluetoothDevice, Void, Boolean> {
        private BluetoothDevice device;

        @Override
        protected Boolean doInBackground(BluetoothDevice... devices) {
            device = devices[0];
            try {
                if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                    return false;
                }

                // Cancel discovery because it will slow down the connection
                bluetoothAdapter.cancelDiscovery();

                // Create a connection to the device
                bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(UUID_BT);
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                return true;
            } catch (IOException e) {
                Log.e(TAG, "Error connecting to device", e);
                try {
                    bluetoothSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Error closing socket", closeException);
                }
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                showToast("Connected to " + device.getName());
            } else {
                showToast("Connection failed");
            }
        }
    }

    private void disconnectFromDevice() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket.close();
                bluetoothSocket = null;
            }
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
            showToast("Disconnected");
        } catch (IOException e) {
            Log.e(TAG, "Error disconnecting", e);
        }
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (receiver != null) {
                unregisterReceiver(receiver);
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Receiver not registered", e);
        }
        disconnectFromDevice();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    showToast("Permission denied: " + permissions[i]);
                    return;
                }
            }
            // All permissions granted, proceed with Bluetooth operations
            checkLocationEnabled();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                checkLocationEnabled();
            } else {
                showToast("Bluetooth is required for this app");
            }
        } else if (requestCode == REQUEST_ENABLE_LOCATION) {
            checkLocationEnabled();
        }
    }
}