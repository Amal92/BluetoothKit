package com.amp.bluetoothkit;

public interface BluetoothCallback {
    void Connected();

    void Disconnected();

    void MessageSent();

    void Unsupported();

    void ConnectionFailed(Exception e, String message);

    void MessageFailed(Exception e, String message);
}
