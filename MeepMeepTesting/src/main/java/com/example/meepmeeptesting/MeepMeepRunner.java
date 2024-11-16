package com.example.meepmeeptesting;

import com.example.auton.ActionFunction;
import com.example.auton.Runner;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepRunner implements Runner {
    private RoadRunnerBotEntity bot;
    MeepMeepRunner(RoadRunnerBotEntity myBot){
        bot = myBot;
    }
    public void runAction(ActionFunction actionFunction) {
        bot.runAction(actionFunction.evaluate(bot.getDrive().actionBuilder(bot.getPose())));
    }
}
