package nz.ac.ara.dtw0048.lyricscroller.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import io.reactivex.rxjava3.core.Completable;
import nz.ac.ara.dtw0048.lyricscroller.R;
import nz.ac.ara.dtw0048.lyricscroller.controller.Controller;
import nz.ac.ara.dtw0048.lyricscroller.model.SetlistSong;
import nz.ac.ara.dtw0048.lyricscroller.model.Song;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Controller controller = Controller.getInstance();
        controller.openDatabase(this);

        Completable.mergeArray(
            controller.addSetlist("Test Setlist 1"),
            controller.addSetlist("Test Setlist 2"),

            controller.addSong(new Song("Test Song 1", "Test Artist 1", TEST_LYRICS)),
            controller.addSong(new Song("Test Song 2", "Test Artist 2", TEST_LYRICS)),
            controller.addSong(new Song("Test Song 3", "Test Artist 1", TEST_LYRICS)),

            controller.addSetlistSong(new SetlistSong("Test Song 1", "Test Artist 1", "Test Setlist 1")),
            controller.addSetlistSong(new SetlistSong("Test Song 2", "Test Artist 2", "Test Setlist 2")),
            controller.addSetlistSong(new SetlistSong("Test Song 3", "Test Artist 1", "Test Setlist 1")),
            controller.addSetlistSong(new SetlistSong("Test Song 3", "Test Artist 1", "Test Setlist 2"))
        ).subscribe();
    }


    private final static String TEST_LYRICS = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
                        "Nam viverra nisi a justo dapibus, sit amet venenatis mauris condimentum.\n" +
                        "Integer a dui eu quam mollis consequat eu non felis.\n" +
                        "\n" +
                        "Suspendisse ut diam nec purus volutpat elementum.\n" +
                        "Aliquam mattis arcu in rutrum vulputate.\n" +
                        "Quisque ullamcorper enim ac arcu laoreet, a eleifend magna lobortis.\n" +
                        "Suspendisse et nulla malesuada, euismod nibh et, ultrices mi.\n" +
                        "Nullam sollicitudin ligula et ligula commodo vehicula.\n" +
                        "Duis feugiat eros non orci lacinia dapibus.\n" +
                        "\n" +
                        "Ut feugiat sapien fringilla, tristique mi posuere, ornare risus.\n" +
                        "Nullam ac turpis ac eros laoreet vulputate.\n" +
                        "Praesent aliquet sapien convallis, posuere dui at, fermentum dui.\n" +
                        "Fusce ultricies mauris eget justo interdum feugiat.\n" +
                        "Donec pellentesque massa vel nulla pharetra, ut varius massa dictum.\n" +
                        "\n" +
                        "Donec mattis ligula eget leo scelerisque, non tempor libero iaculis.\n" +
                        "Vivamus lobortis urna in augue varius suscipit.\n" +
                        "Curabitur ac nibh nec dolor dapibus porttitor vitae ut lorem.\n" +
                        "Proin commodo augue dignissim, volutpat est id, efficitur velit.\n" +
                        "Nulla efficitur est ac nisl semper pellentesque.\n" +
                        "Pellentesque ut tortor sed lectus porttitor hendrerit vitae sed justo.\n" +
                        "\n" +
                        "Ut blandit augue eu feugiat suscipit.\n" +
                        "Donec porttitor tellus nec tellus pellentesque bibendum.\n" +
                        "Quisque sit amet sapien rhoncus, convallis ante ultricies, tristique est.\n" +
                        "Nunc viverra enim non dignissim eleifend.\n" +
                        "Suspendisse vitae nisl pretium nisl interdum facilisis.\n" +
                        "\n" +
                        "Phasellus non sapien nec ante accumsan porta ac feugiat mauris.\n" +
                        "Praesent non sem maximus, ultrices risus non, auctor magna.\n" +
                        "Curabitur dapibus ante sed vestibulum tincidunt.\n" +
                        "Etiam tempus libero in ligula semper faucibus.\n" +
                        "\n" +
                        "Cras eu tortor malesuada, lobortis leo ut, rutrum tellus.\n" +
                        "Praesent pretium erat sit amet ante varius, nec convallis diam ultrices.\n" +
                        "Fusce id turpis in risus aliquet finibus.\n" +
                        "Suspendisse porta purus maximus est rutrum, nec commodo augue tempor.\n" +
                        "Vivamus egestas felis quis dolor pretium porta.\n" +
                        "\n" +
                        "In malesuada lorem vitae libero eleifend facilisis.\n" +
                        "Etiam non ligula nec velit placerat interdum ut ac mauris.\n" +
                        "Maecenas sed tellus sit amet risus scelerisque consectetur.\n" +
                        "Nam eget nunc eu nisi efficitur dapibus nec eget nulla.\n" +
                        "Cras eget velit eget metus posuere facilisis eu eget sem.\n" +
                        "\n" +
                        "Vestibulum sed dolor eget risus tristique hendrerit.\n" +
                        "Nunc dapibus mi scelerisque neque pellentesque, ut pharetra tortor interdum.\n" +
                        "Curabitur consequat urna vel accumsan fringilla.\n" +
                        "Ut et sapien accumsan, interdum neque in, rhoncus lectus.\n" +
                        "\n" +
                        "Nullam luctus erat et lorem rutrum, eu suscipit magna elementum.\n" +
                        "Integer pretium nulla ac libero dapibus pulvinar.\n" +
                        "Nullam nec ante accumsan, gravida magna in, consectetur arcu.\n" +
                        "Etiam semper dui id nisl varius, sed lacinia est commodo.\n" +
                        "Donec vitae nulla cursus, tempor elit ac, dictum erat.\n";
}