package antlr;// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Pds.g4 by ANTLR 4.5.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PdsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, GAMMA=5, TO=6, DIGIT=7, ID=8, WS=9;
	public static final int
		RULE_pds = 0, RULE_start = 1, RULE_rules = 2, RULE_trans_rule = 3, RULE_conf = 4, 
		RULE_stack = 5, RULE_stack_content = 6;
	public static final String[] ruleNames = {
		"pds", "start", "rules", "trans_rule", "conf", "stack", "stack_content"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'<'", "'>'", null, "'-->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "GAMMA", "TO", "DIGIT", "ID", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Pds.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PdsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PdsContext extends ParserRuleContext {
		public StartContext start() {
			return getRuleContext(StartContext.class,0);
		}
		public RulesContext rules() {
			return getRuleContext(RulesContext.class,0);
		}
		public PdsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pds; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterPds(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitPds(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitPds(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PdsContext pds() throws RecognitionException {
		PdsContext _localctx = new PdsContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pds);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(14);
			start();
			setState(15);
			rules();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StartContext extends ParserRuleContext {
		public ConfContext conf() {
			return getRuleContext(ConfContext.class,0);
		}
		public StartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterStart(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitStart(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitStart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartContext start() throws RecognitionException {
		StartContext _localctx = new StartContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_start);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(17);
			match(T__0);
			setState(18);
			conf();
			setState(19);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RulesContext extends ParserRuleContext {
		public List<Trans_ruleContext> trans_rule() {
			return getRuleContexts(Trans_ruleContext.class);
		}
		public Trans_ruleContext trans_rule(int i) {
			return getRuleContext(Trans_ruleContext.class,i);
		}
		public RulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterRules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitRules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesContext rules() throws RecognitionException {
		RulesContext _localctx = new RulesContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(21);
				trans_rule();
				}
				}
				setState(26);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Trans_ruleContext extends ParserRuleContext {
		public List<ConfContext> conf() {
			return getRuleContexts(ConfContext.class);
		}
		public ConfContext conf(int i) {
			return getRuleContext(ConfContext.class,i);
		}
		public TerminalNode TO() { return getToken(PdsParser.TO, 0); }
		public Trans_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trans_rule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterTrans_rule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitTrans_rule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitTrans_rule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Trans_ruleContext trans_rule() throws RecognitionException {
		Trans_ruleContext _localctx = new Trans_ruleContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_trans_rule);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			conf();
			setState(28);
			match(TO);
			setState(29);
			conf();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConfContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(PdsParser.ID, 0); }
		public StackContext stack() {
			return getRuleContext(StackContext.class,0);
		}
		public ConfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterConf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitConf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitConf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfContext conf() throws RecognitionException {
		ConfContext _localctx = new ConfContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_conf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(31);
			match(ID);
			setState(32);
			stack();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StackContext extends ParserRuleContext {
		public Stack_contentContext stack_content() {
			return getRuleContext(Stack_contentContext.class,0);
		}
		public StackContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stack; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterStack(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitStack(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitStack(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackContext stack() throws RecognitionException {
		StackContext _localctx = new StackContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_stack);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__2);
			setState(35);
			stack_content();
			setState(36);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Stack_contentContext extends ParserRuleContext {
		public List<TerminalNode> GAMMA() { return getTokens(PdsParser.GAMMA); }
		public TerminalNode GAMMA(int i) {
			return getToken(PdsParser.GAMMA, i);
		}
		public Stack_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stack_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).enterStack_content(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof PdsListener ) ((PdsListener)listener).exitStack_content(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PdsVisitor ) return ((PdsVisitor<? extends T>)visitor).visitStack_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stack_contentContext stack_content() throws RecognitionException {
		Stack_contentContext _localctx = new Stack_contentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_stack_content);
		int _la;
		try {
			setState(43);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case GAMMA:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(39);
				match(GAMMA);
				}
				setState(41);
				_la = _input.LA(1);
				if (_la==GAMMA) {
					{
					setState(40);
					match(GAMMA);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\13\60\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\3\3\4\7\4\31\n\4\f\4\16\4\34\13\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7"+
		"\3\7\3\7\3\b\3\b\3\b\5\b,\n\b\5\b.\n\b\3\b\2\2\t\2\4\6\b\n\f\16\2\2+\2"+
		"\20\3\2\2\2\4\23\3\2\2\2\6\32\3\2\2\2\b\35\3\2\2\2\n!\3\2\2\2\f$\3\2\2"+
		"\2\16-\3\2\2\2\20\21\5\4\3\2\21\22\5\6\4\2\22\3\3\2\2\2\23\24\7\3\2\2"+
		"\24\25\5\n\6\2\25\26\7\4\2\2\26\5\3\2\2\2\27\31\5\b\5\2\30\27\3\2\2\2"+
		"\31\34\3\2\2\2\32\30\3\2\2\2\32\33\3\2\2\2\33\7\3\2\2\2\34\32\3\2\2\2"+
		"\35\36\5\n\6\2\36\37\7\b\2\2\37 \5\n\6\2 \t\3\2\2\2!\"\7\n\2\2\"#\5\f"+
		"\7\2#\13\3\2\2\2$%\7\5\2\2%&\5\16\b\2&\'\7\6\2\2\'\r\3\2\2\2(.\3\2\2\2"+
		")+\7\7\2\2*,\7\7\2\2+*\3\2\2\2+,\3\2\2\2,.\3\2\2\2-(\3\2\2\2-)\3\2\2\2"+
		".\17\3\2\2\2\5\32+-";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}