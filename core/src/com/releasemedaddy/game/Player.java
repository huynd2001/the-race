package com.releasemedaddy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

public class Player {
    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 4;
    public static final int WALKING = 0;
    public static final int RUNNING = 1;
    private int currentAnimation;
    private float stateTime = 0.0F;
    private Animation<TextureRegion> walkingAnimation;
    private Animation<TextureRegion> runningAnimation;
    private TextureRegion[] walkingFrames;
    private TextureRegion[] runningFrames;
    private TextureRegion currentFrame;
    private Animation[] animations;
    private Sound stepSound;
    private int stepIndex = 0;

    public Player(TextureRegion textureRegionWalking, TextureRegion textureRegionRunning, Sound stepSound) {
        this.stepSound = stepSound;
        TextureRegion[][] tmp = textureRegionWalking.split(textureRegionWalking.getRegionWidth() / 6, textureRegionWalking.getRegionHeight() / 4);
        this.walkingFrames = new TextureRegion[24];
        int index = 0;

        int i;
        int j;
        for(i = 0; i < 4; ++i) {
            for(j = 0; j < 6; ++j) {
                this.walkingFrames[index++] = tmp[i][j];
            }
        }

        this.walkingAnimation = new Animation(0.05F, this.walkingFrames);
        this.walkingAnimation.setPlayMode(PlayMode.LOOP);
        tmp = textureRegionRunning.split(textureRegionRunning.getRegionWidth() / 6, textureRegionRunning.getRegionHeight() / 4);
        this.runningFrames = new TextureRegion[24];
        index = 0;

        for(i = 0; i < 4; ++i) {
            for(j = 0; j < 6; ++j) {
                this.runningFrames[index++] = tmp[i][j];
            }
        }

        this.runningAnimation = new Animation(0.025F, this.runningFrames);
        this.runningAnimation.setPlayMode(PlayMode.LOOP);
        this.animations = new Animation[2];
        this.animations[0] = this.walkingAnimation;
        this.animations[1] = this.runningAnimation;
        this.setCurrentAnimation(0);
    }

    public void setCurrentAnimation(int currentAnimation) {
        this.currentAnimation = currentAnimation;
        this.stateTime = 0.0F;
        this.stepIndex = 0;
    }

    public int getCurrentAnimation() {
        return this.currentAnimation;
    }

    public void update(SpriteBatch batch) {
        this.stateTime += Gdx.graphics.getDeltaTime();
        if (this.animations[this.currentAnimation].getKeyFrameIndex(this.stateTime) != this.stepIndex && (this.animations[this.currentAnimation].getKeyFrameIndex(this.stateTime) == 0 || this.animations[this.currentAnimation].getKeyFrameIndex(this.stateTime) == 12)) {
            this.stepIndex = this.animations[this.currentAnimation].getKeyFrameIndex(this.stateTime);
            this.stepSound.play();
        }

        this.currentFrame = (TextureRegion)this.animations[this.currentAnimation].getKeyFrame(this.stateTime, true);
        batch.begin();
        batch.draw(this.currentFrame, (float)(Gdx.graphics.getWidth() / 2 - this.currentFrame.getRegionWidth() / 2), (float)(Gdx.graphics.getHeight() / 2 - this.currentFrame.getRegionHeight() / 2));
        batch.end();
    }
}
