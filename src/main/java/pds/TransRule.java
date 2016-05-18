package pds;

import antlr.PdsParser;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.yarn.webapp.hamlet.HamletSpec;

/**
 * Created by Cynric on 5/18/16.
 */
public class TransRule {
    Configuration startConf;
    Configuration endConf;


    public TransRule(Configuration startConf, Configuration endConf) {
        this.startConf = startConf;
        this.endConf = endConf;
    }

    public static TransRule fromTransContext(PdsParser.Trans_ruleContext ctx) {
        PdsParser.ConfContext startConf = (PdsParser.ConfContext) ctx.getChild(0);
        PdsParser.ConfContext endConf = (PdsParser.ConfContext) ctx.getChild(2);

        return new TransRule(Configuration.fromAntlrContext(startConf),
                Configuration.fromAntlrContext(endConf));

    }

}
