package com.example.auton;

public interface IBeak {
    void MoveArm(double position);

    void DrivePosition();

    void PrepForPickup();

    void PickupReach();

    void PickupReachMiddle();

    void CloseBeak();

    void OpenBeak();

    void ToggleBeak();

    void changingArmUp();

    void changingArmDown();

    void beakStart();
}
