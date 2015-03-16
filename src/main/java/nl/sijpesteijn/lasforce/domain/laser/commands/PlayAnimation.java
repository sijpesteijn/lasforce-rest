package nl.sijpesteijn.lasforce.domain.laser.commands;

import nl.sijpesteijn.lasforce.services.AnimationInfo;

/**
 * @author Gijs Sijpesteijn
 */
public class PlayAnimation extends Command {
    private AnimationInfo animationInfo;

    public PlayAnimation(AnimationInfo animationInfo) {
        super("play_animation");
        this.animationInfo = animationInfo;
    }

    public AnimationInfo getAnimationInfo() {
        return animationInfo;
    }
}
