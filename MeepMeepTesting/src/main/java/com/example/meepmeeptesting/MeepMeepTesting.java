package com.example.meepmeeptesting;

import com.example.auton.Auton;
import com.example.auton.AutonBlue;
import com.example.auton.AutonRed;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Objects;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        Auton auton;
        if(Objects.equals(args[0], "blue")){
            auton = new AutonBlue();
        } else if (Objects.equals(args[0], "red")) {
            auton = new AutonRed();
        }
        else{
            throw new RuntimeException("No Team Specified; Unable to start");
        }
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .setColorScheme(auton.getClass() == AutonBlue.class ? new ColorSchemeBlueDark() : new ColorSchemeRedDark())
                .setStartPose(auton.getStartingPose())
                .build();

        auton.Run(new MeepMeepRunner(myBot));

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}