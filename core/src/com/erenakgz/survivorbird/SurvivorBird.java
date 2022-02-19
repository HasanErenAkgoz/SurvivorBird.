package com.erenakgz.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture bird;
    Texture bee;
    Texture bee2;
    Texture bee3;
    BitmapFont scoreFont;
    BitmapFont gameOver;
    float birdx = 0;
    float birdy = 0;
    int gameState = 0;
    float velocity = 0;
    float gravity = 0.3f;
    Random random;
    float enemyVelocity = 7;
    int numberOfEnemy = 4;
    Circle birdCricle;
    ShapeRenderer shapeRenderer;
    float[] enemyX = new float[numberOfEnemy];
    float[] enemyOfSet = new float[numberOfEnemy];
    float[] enemyOfSet2 = new float[numberOfEnemy];
    float[] enemyOfSet3 = new float[numberOfEnemy];
    Circle[] enemyCricle;
    Circle[] enemyCricle2;
    Circle[] enemyCricle3;
    int score = 0;
    int scoreEnemy = 0;


    float distance;

    @Override
    public void create() { //oncreate kısmı gibi düşünebilriiz.
        batch = new SpriteBatch();
        background = new Texture("background.png");
        bird = new Texture("bird.png");
        bee = new Texture("bee.png");
        bee2 = new Texture("bee.png");
        bee3 = new Texture("bee.png");
        scoreFont = new BitmapFont();
        gameOver = new BitmapFont();

        scoreFont.setColor(Color.WHITE);
        scoreFont.getData().setScale(4);

        gameOver.setColor(Color.RED);
        gameOver.getData().setScale(6);
        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();
        birdx = Gdx.graphics.getWidth() / 10;
        birdy = Gdx.graphics.getHeight() / 3;
        birdCricle = new Circle();
        shapeRenderer = new ShapeRenderer();
        enemyCricle = new Circle[numberOfEnemy];
        enemyCricle2 = new Circle[numberOfEnemy];
        enemyCricle3 = new Circle[numberOfEnemy];

        for (int i = 0; i < numberOfEnemy; i++) {
            randomEnemyOfSet(i);
            enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
            enemyCricle[i] = new Circle();
            enemyCricle2[i] = new Circle();
            enemyCricle3[i] = new Circle();
        }


    }

    @Override
    public void render() { //oyun devam ederken sürekli çağırılan bir metod
        GameStart();
    }

    @Override
    public void dispose() {

    }

    public void GameStart() {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1) {
            if (enemyX[scoreEnemy] < birdx) {
                score++;
                if (scoreEnemy < numberOfEnemy - 1) {
                    scoreEnemy++;
                } else {
                    scoreEnemy = 0;
                }
            }


            if (Gdx.input.justTouched()) {
                velocity = -10;

            }

            for (int i = 0; i < numberOfEnemy; i++) {
                if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
                    enemyX[i] = enemyX[i] + numberOfEnemy * distance;
                    randomEnemyOfSet(i);
                }

                enemyX[i] = enemyX[i] - enemyVelocity;
                batch.draw(bee, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(bee, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                batch.draw(bee, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOfSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
                enemyCricle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOfSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
                enemyCricle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOfSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
                enemyCricle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + enemyOfSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);

            }

            if (birdy > 0) {
                velocity = velocity + gravity;
                birdy = birdy - velocity;
            } else {
                gameState = 2;
            }

        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {
            gameOver.draw(batch, "Game Over Tap To Play Again!", 100, Gdx.graphics.getHeight() / 2);
            if (Gdx.input.justTouched()) {
                gameState = 1;
                birdy = Gdx.graphics.getHeight() / 3;
                for (int i = 0; i < numberOfEnemy; i++) {
                    randomEnemyOfSet(i);
                    enemyX[i] = Gdx.graphics.getWidth() - bee.getWidth() / 2 + i * distance;
                    enemyCricle[i] = new Circle();
                    enemyCricle2[i] = new Circle();
                    enemyCricle3[i] = new Circle();
                }

                velocity = 0;
                score = 0;
                scoreEnemy = 0;
            }
        }


        batch.draw(bird, birdx, birdy, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10); // Görselimizin Başlangıç noktası x,y ve boyutlarını belirliyoruz
        scoreFont.draw(batch, String.valueOf(score), 100, 200);
        batch.end();

        birdCricle.set(birdx + Gdx.graphics.getWidth() / 30, birdy + Gdx.graphics.getHeight() / 30, Gdx.graphics.getWidth() / 30);
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // shapeRenderer.setColor(Color.BLACK);
        //shapeRenderer.circle(birdCricle.x, birdCricle.y, birdCricle.radius);
        for (int i = 0; i < numberOfEnemy; i++) {
            if (Intersector.overlaps(birdCricle, enemyCricle[i]) || Intersector.overlaps(birdCricle, enemyCricle2[i])
                    || Intersector.overlaps(birdCricle, enemyCricle3[i])) {
                gameState = 2;
            }
        }
        shapeRenderer.end();
    }

    public void randomEnemyOfSet(int i) {
        enemyOfSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
        enemyOfSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
        enemyOfSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
    }

}
