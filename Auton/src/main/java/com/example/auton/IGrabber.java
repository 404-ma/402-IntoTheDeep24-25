package com.example.auton;

public interface IGrabber {
    void Open();

    void Close();

    void SetHeight(int position);

    void GoToHighBar();

    void GoToLowBar();

    void GoToPickupHeight(boolean override);

    void HangSample();

    boolean CheckForBrake();
}
