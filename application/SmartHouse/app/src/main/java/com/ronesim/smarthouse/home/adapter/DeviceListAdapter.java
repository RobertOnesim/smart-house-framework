package com.ronesim.smarthouse.home.adapter;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.ronesim.smarthouse.R;
import com.ronesim.smarthouse.model.Product;
import com.ronesim.smarthouse.model.devices.Device;
import com.ronesim.smarthouse.model.devices.DeviceUpdateStateVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by ronesim on 07.05.2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceHolder> {

    private static final long SCAN_PERIOD = 3000;
    // progress dialog used for BLE scan
    private ProgressDialog progressDialog;
    private AppCompatSpinner productTypeSpinner;
    private AppCompatSpinner productNameSpinner;
    private List<Device> devicesList = Collections.emptyList();
    private List<Product> products;
    // used for bluetooth scan BLE devices
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner mLEScanner;
    private Handler handler = new Handler();
    private List<ScanFilter> filters = new ArrayList<ScanFilter>();
    private List<BluetoothDevice> devices;
    private ScanSettings settings;

    public DeviceListAdapter(List<Device> devicesList, List<Product> products) {
        this.devicesList = devicesList;
        this.products = products;
    }

    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View itemView;

        if (viewType == R.layout.device_row)
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_row, parent, false);
        else
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_device_button, parent, false);

        return new DeviceHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final DeviceHolder holder, final int position) {
        if (position == devicesList.size()) {
            holder.addDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addNewDevice(view);
                }
            });
        } else {
            holder.setName(devicesList.get(position).getName());
            holder.deviceLogo.setImageResource(devicesList.get(position).getImageLogo());
            holder.turnOnOff.setChecked(devicesList.get(position).isOn());
            holder.turnOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked)
                        devicesList.get(position).accept(new DeviceUpdateStateVisitor("state", "on"));
                    else
                        devicesList.get(position).accept(new DeviceUpdateStateVisitor("state", "off"));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display + the last element (add button)
        return devicesList.size() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == devicesList.size()) ? R.layout.add_device_button : R.layout.device_row;
    }

    public Device getDevice(int position) {
        return devicesList.get(position);
    }

    // Remove a RecyclerView item
    public void remove(int position) {
        devicesList.remove(position);
        notifyItemRemoved(position);
    }

    private void addNewDevice(View view) {
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.add_device_dialog, null);
        // create first spinner
        productTypeSpinner = (AppCompatSpinner) dialogView.findViewById(R.id.productType);
        productNameSpinner = (AppCompatSpinner) dialogView.findViewById(R.id.productName);
        ArrayAdapter productsTypeAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, getProductsType());
        productTypeSpinner.setAdapter(productsTypeAdapter);

        productTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = productTypeSpinner.getSelectedItem().toString();
                ArrayAdapter<String> productsNameAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, getProductsName(type));
                productNameSpinner.setAdapter(productsNameAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                .setTitle("Add Device")
                .setView(dialogView)
                .setPositiveButton("Search", null)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        final EditText inputDeviceName = (EditText) dialogView.findViewById(R.id.deviceName);
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
                    @Override
                    public void onClick(View view) {
                        // validate name
                        String roomName = inputDeviceName.getText().toString();
                        if (roomName.isEmpty()) {
                            inputDeviceName.setError("Room name cannot be empty.");
                        } else {
                            // depending on item selected need to find the device type
                            String type = productTypeSpinner.getSelectedItem().toString();
                            String name = productNameSpinner.getSelectedItem().toString();

                            // show progress dialog until search is finished
                            progressDialog = new ProgressDialog(view.getContext(),
                                    R.style.AppTheme_Login_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Searching ...");
                            progressDialog.show();
                            // scanning result
                            devices = new ArrayList<BluetoothDevice>();
                            switch (type) {
                                case "Light":
                                    switch (name) {
                                        case "MagicBlue":
                                            // search MagicBLue light using BLE devices search android
                                            final BluetoothManager bluetoothManager = (BluetoothManager)
                                                    view.getContext().getSystemService(Context.BLUETOOTH_SERVICE);
                                            bluetoothAdapter = bluetoothManager.getAdapter();
                                            if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled())
                                                Toast.makeText(view.getContext(), "Bluetooth is not enabled.", Toast.LENGTH_SHORT).show();
                                            else {
                                                if (Build.VERSION.SDK_INT >= 21) {
                                                    mLEScanner = bluetoothAdapter.getBluetoothLeScanner();
                                                    settings = new ScanSettings.Builder()
                                                            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                                                            .build();
                                                    scanLeDevicesAPI21(true);
                                                } else
                                                    scanLeDevicesAPI18(true);
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "Plug":
                                    switch (name) {
                                        case "TP-Link":
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                default:
                                    break;
                            }
                            alertDialog.dismiss();
                        }
                    }
                });
            }
        });
        alertDialog.show();
    }

    private List<String> getProductsType() {
        HashSet<String> ans = new HashSet<>();
        //List<String> ans = new ArrayList<>();
        for (Product product : products)
            ans.add(product.getType());
        return new ArrayList<>(ans);
    }

    private List<String> getProductsName(String type) {
        List<String> ans = new ArrayList<>();
        for (Product product : products) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Objects.equals(product.getType(), type)) {
                    ans.add(product.getName());
                }
            } else {
                if (product.getType() == type) {
                    ans.add(product.getName());

                }
            }
        }
        return ans;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scanLeDevicesAPI21(final boolean enable) {
        final ScanCallback mScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                devices.add(result.getDevice());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                for (ScanResult result : results)
                    System.out.println("Scan results result: " + result.toString());
            }

            @Override
            public void onScanFailed(int errorCode) {
                System.out.println("Scan failed error code: " + errorCode);
            }
        };
        if (enable) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLEScanner.stopScan(mScanCallback);
                }
            }, SCAN_PERIOD);
            mLEScanner.startScan(filters, settings, mScanCallback);
        } else
            mLEScanner.stopScan(mScanCallback);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevicesAPI18(final boolean enable) {
        final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                devices.add(device);
            }
        };
        if (enable) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    bluetoothAdapter.stopLeScan(mLeScanCallback);
                    // when search finish process the result
                    processResults();
                    progressDialog.dismiss();
                }
            }, SCAN_PERIOD);
            bluetoothAdapter.startLeScan(mLeScanCallback);
        } else
            bluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    private void processResults() {
        HashSet<String> lights = new HashSet<>();
        for (BluetoothDevice bd : devices)
            if (bd.getName().startsWith("LEDBLE") && !lights.contains(bd.getAddress())) {
                lights.add(bd.getAddress());
                System.out.println("HERE " + bd.getAddress());
            }
    }
}