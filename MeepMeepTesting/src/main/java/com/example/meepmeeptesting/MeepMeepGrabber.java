package com.example.meepmeeptesting;

import com.example.auton.IGrabber;

public class MeepMeepGrabber implements IGrabber {
    public void Open() {
        System.out.println("Open claw");
    }

    public void Close() {
        System.out.println("Close claw");
    }

    public void SetHeight(int position) {
        System.out.print("Position is now: ");
        System.out.println(position);
    }

    public void ToggleClaw() {
        System.out.println("Toggle Claw");
    }

    public void GoToHighBar() {
        System.out.println("Go to high bar");
    }

    public void GoToLowBar() {
        System.out.println("Go to low bar");
    }

    public void GoToPickupHeight() {
        System.out.println("Go to pickup height");
    }

    public void HangSample() {
        System.out.println("Hang sample");
    }

    public boolean CheckForBrake() {
        return false;
    }
}
