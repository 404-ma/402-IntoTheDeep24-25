package com.example.meepmeeptesting;

import com.example.auton.IBeak;

public class MeepMeepBeak implements IBeak {
    public void MoveArm(double position) {
        System.out.print("Move arm to ");
        System.out.println(position);
    }

    public void DrivePosition() {
        System.out.println("Drive position");
    }

    public void PrepForPickup() {
        System.out.println("Prepare for pickup");
    }

    public void PickupReach() {
        System.out.println("Pickup reach");
    }

    public void PickupReachMiddle() {
        System.out.println("Pickup reach middle");
    }

    public void CloseBeak() {
        System.out.println("Close beak");
    }

    public void OpenBeak() {
        System.out.println("Open beak");
    }

    public void ToggleBeak() {
        System.out.println("Toggle beak");
    }

    public void changingArmUp() {
        System.out.println("changing arm up");
    }

    public void changingArmDown() {
        System.out.println("changing arm down");
    }

    public void PickupReachMaximum() {
        System.out.println("Pickup Reach Maximum");
    }

    public void PickupReachClose() {
        System.out.println("Pickup Reach Close");
    }

    public void beakStart() {
        System.out.println("beak start");
    }

    public void SuplexSample() {
        System.out.println("Suplex Sample");
    }

}
