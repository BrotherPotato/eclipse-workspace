package ServerTCPP.Server;

import ServerTCPP.Client.Shot;

import java.util.ArrayList;

public class Server extends Thread {

    static int PORT = 6066;
    private static ArrayList<PlayerHandler> players = new ArrayList<>();
    private ArrayList<Shot> shots = new ArrayList<>();

    public static void main(String[] args) {
        new Server();
    }

    private Server() {
        new Connector(players);
        this.start();
    }

    /** @noinspection InfiniteLoopStatement*/
    public void run() {
        while (true) {
            for (PlayerHandler p : players) {
                p.player.movePlayer();
                if (p.player.mousepressed && p.player.cd == 0) {
                    p.player.shoot = true;
                    p.player.shoot(shots, p.team);
                }
            }
            for (Shot sht : shots) {
                boolean reset = false;
                sht.moveShot();
                for (PlayerHandler p : players) {
                    if (sht.checkHit(p.player, p.team)) {
                        p.player.setHp(p.player.getHp() - 1);
                        if (p.player.getHp() <= 0) {
                            resetPlayers();
                            if (p.equals(players.get(1))) {
                                players.get(0).player.setPoints(players.get(0).player.getPoints() + 1);
                            } else {
                                players.get(1).player.setPoints(players.get(1).player.getPoints() + 1);
                            }
                            shots.clear();
                            reset = true;
                        }
                        sht.bounces = 6;
                    }
                }
                if (reset) {
                    shots.clear();
                    break;
                }
            }
            for (Shot sht : shots) {
                if (sht.bounces > 5) {
                    shots.remove(sht);
                    break;
                }
            }
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void resetPlayers() {
        for (PlayerHandler p: players){
            p.player.setX(100+1720*p.team);
            p.player.setY(1000);
            p.player.stopySpeed();
            p.player.stopxSpeed();
            p.player.setHp(5);
        }
    }
}
