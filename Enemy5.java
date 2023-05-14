package shoot;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy5 {
    Image image;
    int x;
    int y;
    int width;
    int height;
    int hp;
    int velocityX; // 가로 속도
    int moveDuration; // 움직임 지속 시간 (프레임 수)
    int stopDuration; // 멈춤 지속 시간 (프레임 수)
    int patternTimer; // 패턴 타이머

    public Enemy5(int x, int y) {
        image = new ImageIcon("src/images/enemy5.png").getImage();
        width = image.getWidth(null);
        height = image.getHeight(null);
        hp = 100;
        this.x = x;
        this.y = y;
        velocityX = 10; // 가로 속도 설정
        moveDuration = 60; // 움직임 지속 시간 (프레임 수)
        stopDuration = 30; // 멈춤 지속 시간 (프레임 수)
        patternTimer = 0; // 패턴 타이머 초기화
    }

    public void move() {
        // 패턴 타이머 증가
        patternTimer++;

        // 패턴 타이머에 따라 움직임 또는 멈춤
        if (patternTimer <= moveDuration) {
            // 움직임 상태
            x -= velocityX; // 적의 가로 이동
        } else if (patternTimer > moveDuration + stopDuration) {
            // 멈춤 상태
            patternTimer = 0; // 패턴 타이머 초기화
        }
    }
}
