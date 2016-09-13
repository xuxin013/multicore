package petersonTournament;

public class Tournament {
    public Peterson[] p;
    public int threadnumber;

    public Tournament() {
        p = new Peterson[8];
        for (int i = 0; i < 8; i++) {
            p[i] = new Peterson();
        }
    }

    public void requestCS(int id) {
        p[id / 2].requestCS(id % 2);
        p[id / 4 + 4].requestCS(id % 4 / 2);
        p[id / 8 + 6].requestCS(id / 4);
    }

    public void releaseCS(int id) {
        p[id / 8 + 6].releaseCS(id / 4);
        p[id / 4 + 4].releaseCS(id % 4 / 2);
        p[id / 2].releaseCS(id % 2);

    }
}
