import antlr.simple.Container;
import analyzer.serial.SerialPdsAnalyzer;

/**
 * Created by Cynric on 12/10/2016.
 */
public class Application {
    public static void main(String[] args) {
        String inputFile = "paper";
        Container container = Container.parseInputFile("example/" + inputFile + ".pds");


        SerialPdsAnalyzer serialPdsAnalyzer = new SerialPdsAnalyzer();
        serialPdsAnalyzer.computePre(container);
    }
}
