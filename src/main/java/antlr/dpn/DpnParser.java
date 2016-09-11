// Generated from /Users/cynric/workspaces/antlr/pds/pds/src/Dpn.g4 by ANTLR 4.5.3
package antlr.dpn;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class DpnParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, TO=7, ID=8, WS=9;
	public static final int
		RULE_dpn = 0, RULE_transList = 1, RULE_ruleList = 2, RULE_trans = 3, RULE_trans_rule = 4, 
		RULE_multi = 5, RULE_conf = 6, RULE_stack = 7, RULE_stack_content = 8;
	public static final String[] ruleNames = {
		"dpn", "transList", "ruleList", "trans", "trans_rule", "multi", "conf", 
		"stack", "stack_content"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'/'", "'{'", "'}'", "','", "'<'", "'>'", "'-->'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, "TO", "ID", "WS"
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
	public String getGrammarFileName() { return "Dpn.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DpnParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DpnContext extends ParserRuleContext {
		public TransListContext transList() {
			return getRuleContext(TransListContext.class,0);
		}
		public RuleListContext ruleList() {
			return getRuleContext(RuleListContext.class,0);
		}
		public DpnContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dpn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterDpn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitDpn(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitDpn(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DpnContext dpn() throws RecognitionException {
		DpnContext _localctx = new DpnContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_dpn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			transList();
			setState(19);
			ruleList();
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

	public static class TransListContext extends ParserRuleContext {
		public List<TransContext> trans() {
			return getRuleContexts(TransContext.class);
		}
		public TransContext trans(int i) {
			return getRuleContext(TransContext.class,i);
		}
		public TransListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_transList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterTransList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitTransList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitTransList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TransListContext transList() throws RecognitionException {
		TransListContext _localctx = new TransListContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_transList);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(21);
					trans();
					}
					} 
				}
				setState(26);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
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

	public static class RuleListContext extends ParserRuleContext {
		public List<Trans_ruleContext> trans_rule() {
			return getRuleContexts(Trans_ruleContext.class);
		}
		public Trans_ruleContext trans_rule(int i) {
			return getRuleContext(Trans_ruleContext.class,i);
		}
		public RuleListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterRuleList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitRuleList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitRuleList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleListContext ruleList() throws RecognitionException {
		RuleListContext _localctx = new RuleListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_ruleList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(27);
				trans_rule();
				}
				}
				setState(32);
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

	public static class TransContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DpnParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DpnParser.ID, i);
		}
		public List<MultiContext> multi() {
			return getRuleContexts(MultiContext.class);
		}
		public MultiContext multi(int i) {
			return getRuleContext(MultiContext.class,i);
		}
		public TerminalNode TO() { return getToken(DpnParser.TO, 0); }
		public TransContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trans; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterTrans(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitTrans(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitTrans(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TransContext trans() throws RecognitionException {
		TransContext _localctx = new TransContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_trans);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			match(ID);
			setState(34);
			match(ID);
			setState(35);
			match(T__0);
			setState(36);
			multi();
			setState(37);
			match(TO);
			setState(38);
			multi();
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
		public List<TerminalNode> TO() { return getTokens(DpnParser.TO); }
		public TerminalNode TO(int i) {
			return getToken(DpnParser.TO, i);
		}
		public Trans_ruleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trans_rule; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterTrans_rule(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitTrans_rule(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitTrans_rule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Trans_ruleContext trans_rule() throws RecognitionException {
		Trans_ruleContext _localctx = new Trans_ruleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_trans_rule);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			conf();
			setState(41);
			match(TO);
			setState(42);
			conf();
			setState(45);
			_la = _input.LA(1);
			if (_la==TO) {
				{
				setState(43);
				match(TO);
				setState(44);
				conf();
				}
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

	public static class MultiContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(DpnParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DpnParser.ID, i);
		}
		public MultiContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multi; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterMulti(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitMulti(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitMulti(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiContext multi() throws RecognitionException {
		MultiContext _localctx = new MultiContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_multi);
		int _la;
		try {
			setState(61);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(T__1);
				setState(49);
				match(ID);
				setState(50);
				match(T__2);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(51);
				match(T__1);
				setState(52);
				match(ID);
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(53);
					match(T__3);
					setState(54);
					match(ID);
					}
					}
					setState(59);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(60);
				match(T__2);
				}
				break;
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
		public TerminalNode ID() { return getToken(DpnParser.ID, 0); }
		public StackContext stack() {
			return getRuleContext(StackContext.class,0);
		}
		public ConfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_conf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterConf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitConf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitConf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConfContext conf() throws RecognitionException {
		ConfContext _localctx = new ConfContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_conf);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(ID);
			setState(64);
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
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterStack(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitStack(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitStack(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StackContext stack() throws RecognitionException {
		StackContext _localctx = new StackContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_stack);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__4);
			setState(67);
			stack_content();
			setState(68);
			match(T__5);
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
		public List<TerminalNode> ID() { return getTokens(DpnParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(DpnParser.ID, i);
		}
		public Stack_contentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stack_content; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).enterStack_content(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DpnListener ) ((DpnListener)listener).exitStack_content(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof DpnVisitor ) return ((DpnVisitor<? extends T>)visitor).visitStack_content(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Stack_contentContext stack_content() throws RecognitionException {
		Stack_contentContext _localctx = new Stack_contentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_stack_content);
		int _la;
		try {
			setState(75);
			switch (_input.LA(1)) {
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(71);
				match(ID);
				}
				setState(73);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(72);
					match(ID);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\13P\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3\2"+
		"\3\3\7\3\31\n\3\f\3\16\3\34\13\3\3\4\7\4\37\n\4\f\4\16\4\"\13\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\5\6\60\n\6\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\7\7:\n\7\f\7\16\7=\13\7\3\7\5\7@\n\7\3\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\5\nL\n\n\5\nN\n\n\3\n\2\2\13\2\4\6\b\n\f\16\20"+
		"\22\2\2N\2\24\3\2\2\2\4\32\3\2\2\2\6 \3\2\2\2\b#\3\2\2\2\n*\3\2\2\2\f"+
		"?\3\2\2\2\16A\3\2\2\2\20D\3\2\2\2\22M\3\2\2\2\24\25\5\4\3\2\25\26\5\6"+
		"\4\2\26\3\3\2\2\2\27\31\5\b\5\2\30\27\3\2\2\2\31\34\3\2\2\2\32\30\3\2"+
		"\2\2\32\33\3\2\2\2\33\5\3\2\2\2\34\32\3\2\2\2\35\37\5\n\6\2\36\35\3\2"+
		"\2\2\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2\2!\7\3\2\2\2\" \3\2\2\2#$\7\n\2"+
		"\2$%\7\n\2\2%&\7\3\2\2&\'\5\f\7\2\'(\7\t\2\2()\5\f\7\2)\t\3\2\2\2*+\5"+
		"\16\b\2+,\7\t\2\2,/\5\16\b\2-.\7\t\2\2.\60\5\16\b\2/-\3\2\2\2/\60\3\2"+
		"\2\2\60\13\3\2\2\2\61@\3\2\2\2\62\63\7\4\2\2\63\64\7\n\2\2\64@\7\5\2\2"+
		"\65\66\7\4\2\2\66;\7\n\2\2\678\7\6\2\28:\7\n\2\29\67\3\2\2\2:=\3\2\2\2"+
		";9\3\2\2\2;<\3\2\2\2<>\3\2\2\2=;\3\2\2\2>@\7\5\2\2?\61\3\2\2\2?\62\3\2"+
		"\2\2?\65\3\2\2\2@\r\3\2\2\2AB\7\n\2\2BC\5\20\t\2C\17\3\2\2\2DE\7\7\2\2"+
		"EF\5\22\n\2FG\7\b\2\2G\21\3\2\2\2HN\3\2\2\2IK\7\n\2\2JL\7\n\2\2KJ\3\2"+
		"\2\2KL\3\2\2\2LN\3\2\2\2MH\3\2\2\2MI\3\2\2\2N\23\3\2\2\2\t\32 /;?KM";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}