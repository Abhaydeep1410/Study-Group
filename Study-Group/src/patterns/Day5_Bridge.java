package patterns;

/* structural design patter
* decouples abstraction fromm its implementation , so that they can vary independently
* ex mobileHdPlayer , webHdplayer , tvHdPlyer ,  all they depped on quality hd
* */

interface VideoQuality{
    void load();
}

class HdQuality implements VideoQuality{
    @Override
    public void load() {
        System.out.println("Hd quality..");
    }
}
class SdQuality implements VideoQuality{
    @Override
    public void load() {
        System.out.println("Sd quality..");
    }
}

abstract class VideoPlayer{
    VideoQuality videoQuality;
    public VideoPlayer(VideoQuality videoQuality){
        this.videoQuality=videoQuality;
    }
    abstract void play();
}

class WebPlayer extends VideoPlayer{
    WebPlayer(VideoQuality videoQuality){
        super(videoQuality);
    }
    @Override
    void play() {
        videoQuality.load();
    }
}

class MobilePlayer extends VideoPlayer{
    MobilePlayer(VideoQuality videoQuality){
        super(videoQuality);
    }
    @Override
    void play() {
        videoQuality.load();
    }
}

public class Day5_Bridge {

    public static void main(String[] args) {
        VideoPlayer vd=new MobilePlayer(new HdQuality());
        vd.play();
    }
}
