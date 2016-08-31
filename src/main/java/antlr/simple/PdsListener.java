package antlr.simple;// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Pds.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link PdsParser}.
 */
public interface PdsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link PdsParser#pds}.
	 * @param ctx the parse tree
	 */
	void enterPds(PdsParser.PdsContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#pds}.
	 * @param ctx the parse tree
	 */
	void exitPds(PdsParser.PdsContext ctx);
	/**
	 * Enter a parse tree produced by {@link PdsParser#start}.
	 * @param ctx the parse tree
	 */
	void enterStart(PdsParser.StartContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#start}.
	 * @param ctx the parse tree
	 */
	void exitStart(PdsParser.StartContext ctx);
	/**
	 * Enter a parse tree produced by {@link PdsParser#rules}.
	 * @param ctx the parse tree
	 */
	void enterRules(PdsParser.RulesContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#rules}.
	 * @param ctx the parse tree
	 */
	void exitRules(PdsParser.RulesContext ctx);
	/**
	 * Enter a parse tree produced by {@link PdsParser#trans_rule}.
	 * @param ctx the parse tree
	 */
	void enterTrans_rule(PdsParser.Trans_ruleContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#trans_rule}.
	 * @param ctx the parse tree
	 */
	void exitTrans_rule(PdsParser.Trans_ruleContext ctx);
	/**
	 * Enter a parse tree produced by {@link PdsParser#conf}.
	 * @param ctx the parse tree
	 */
	void enterConf(PdsParser.ConfContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#conf}.
	 * @param ctx the parse tree
	 */
	void exitConf(PdsParser.ConfContext ctx);
	/**
	 * Enter a parse tree produced by {@link PdsParser#stack}.
	 * @param ctx the parse tree
	 */
	void enterStack(PdsParser.StackContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#stack}.
	 * @param ctx the parse tree
	 */
	void exitStack(PdsParser.StackContext ctx);
	/**
	 * Enter a parse tree produced by {@link PdsParser#stack_content}.
	 * @param ctx the parse tree
	 */
	void enterStack_content(PdsParser.Stack_contentContext ctx);
	/**
	 * Exit a parse tree produced by {@link PdsParser#stack_content}.
	 * @param ctx the parse tree
	 */
	void exitStack_content(PdsParser.Stack_contentContext ctx);
}