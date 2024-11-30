package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.example.auton.ActionFunction;
import com.example.auton.Runner;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
// An Auton runner that will simulate the auton in MeepMeep
public class MeepMeepRunner implements Runner {
    private RoadRunnerBotEntity bot;
    private TrajectoryActionBuilder builder;
    MeepMeepRunner(RoadRunnerBotEntity myBot){
        bot = myBot;
        builder = bot.getDrive().actionBuilder(bot.getPose());
    }
    public void runAction(ActionFunction actionFunction) {
        builder = actionFunction.evaluate(builder);
    }
    public void Finish(){
        bot.runAction(builder.build());
    }
}
