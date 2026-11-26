package game;

import java.util.List;

public class    AllEnemies {
}

class normalZombie extends Enemy {
    public normalZombie(List<Waypoint> path) {
        this(path, 1);
    }

    public normalZombie(List<Waypoint> path, int round) {
        super(Enemy.scaledHp(90, round), 90f, Enemy.scaledReward(6, round), path,
            Enemy.scaledDamage(5, round));
    }
}

class fastZombie extends Enemy {
    public fastZombie(List<Waypoint> path) {
        this(path, 1);
    }

    public fastZombie(List<Waypoint> path, int round) {
        super(Enemy.scaledHp(70, round), 160f, Enemy.scaledReward(9, round), path,
            Enemy.scaledDamage(10, round));
    }
}

class bossZombie extends Enemy {
    public bossZombie(List<Waypoint> path) {
        this(path, 1);
    }

    public bossZombie(List<Waypoint> path, int round) {
        super(Enemy.scaledHp(320, round), 70f, Enemy.scaledReward(28, round), path,
            Enemy.scaledDamage(40, round));
    }
}
