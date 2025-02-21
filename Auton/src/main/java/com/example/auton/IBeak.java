package com.example.auton;

public interface IBeak {
    void MoveArm(double position);

    void DrivePosition();

    void PickupReachClose();

    void PickupReachMiddle();

    void PickupReachMaximum();

    void CloseBeak();

    void OpenBeak();

    void ToggleBeak();

    void changingArmUp();

    void changingArmDown();

    void SuplexSample();

}
