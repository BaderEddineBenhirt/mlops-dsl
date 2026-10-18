package org.xtext.ucd.mlopsdsl.parser.antlr.internal;

import org.eclipse.xtext.*;
import org.eclipse.xtext.parser.*;
import org.eclipse.xtext.parser.impl.*;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.xtext.parser.antlr.AbstractInternalAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.xtext.parser.antlr.XtextTokenStream.HiddenTokens;
import org.eclipse.xtext.parser.antlr.AntlrDatatypeRuleToken;
import org.xtext.ucd.mlopsdsl.services.MlopsDslGrammarAccess;



import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class InternalMlopsDslParser extends AbstractInternalAntlrParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "RULE_STRING", "RULE_INT", "RULE_ID", "RULE_FLOAT", "RULE_ML_COMMENT", "RULE_SL_COMMENT", "RULE_WS", "RULE_ANY_OTHER", "'EnvironmentSpec'", "'{'", "'name'", "'='", "'}'", "'TerraformBackendSpec'", "'kind'", "'bucket'", "'key'", "'resourceGroup'", "'storageAccount'", "'container'", "'NodePoolSpec'", "'machineType'", "'minNodes'", "'desiredNodes'", "'maxNodes'", "'gpu'", "'spot'", "'true'", "'false'", "'RuntimeSpec'", "'nodePools'", "'['", "','", "']'", "'MetaModelSpec'", "'version'", "'schemaVersion'", "'createdBy'", "'createdAt'", "'project'", "'plan'", "'infra'", "'workflow'", "'ProjectSpec'", "'data'", "'features'", "'train'", "'eval'", "'registry'", "'serving'", "'monitoring'", "'tracking'", "'versioning'", "'modelRegistry'", "'decisionPolicy'", "'retrainingPolicy'", "'governance'", "'cicdct'", "'DataAsset'", "'source'", "'modality'", "'location'", "'format'", "'schema'", "'sizeBytes'", "'recordCount'", "'license'", "'privacy'", "'hash'", "'splits'", "'annotations'", "'SplitSpec'", "'ratio'", "'AnnotationSpec'", "'labelSet'", "'guideline'", "'FeatureSet'", "'TrainingSpec'", "'framework'", "'algorithm'", "'hyperparams'", "'image'", "'command'", "'resources'", "'env'", "'Threshold'", "'value'", "'op'", "'EvaluationSpec'", "'metrics'", "'thresholds'", "'RegistrySpec'", "'registerAs'", "'tags'", "'description'", "'stage'", "'ServingSpec'", "'port'", "'healthPath'", "'readinessPath'", "'replicas'", "'autoscaling'", "'timeoutSeconds'", "'routes'", "'DriftMetric'", "'warn'", "'fail'", "'MonitoringSpec'", "'perfMetrics'", "'dataDrift'", "'modelDrift'", "'alertChannel'", "'ExperimentTrackingSpec'", "'backend'", "'experimentName'", "'trackingUri'", "'logParams'", "'logMetrics'", "'logArtifacts'", "'logModel'", "'VersioningSpec'", "'versionData'", "'versionCode'", "'versionModels'", "'versionParams'", "'remoteUri'", "'ModelRegistrySpec'", "'modelName'", "'enableStaging'", "'enableProduction'", "'rollbackEnabled'", "'approvalMode'", "'DecisionCriterion'", "'weight'", "'minValue'", "'maxValue'", "'required'", "'DecisionPolicy'", "'autoPromote'", "'minAccuracy'", "'minF1'", "'maxLatencyMs'", "'maxCost'", "'maxDrift'", "'minTraceabilityScore'", "'criteria'", "'RetrainingPolicy'", "'strategy'", "'driftThreshold'", "'performanceDropThreshold'", "'schedule'", "'autoTrigger'", "'retrainingQueue'", "'GovernanceSpec'", "'traceabilityRequired'", "'explainDecision'", "'auditTrail'", "'riskReport'", "'complianceFramework'", "'PipelineStage'", "'tool'", "'outputArtifact'", "'CiCdCtSpec'", "'target'", "'pipelineFile'", "'enableCI'", "'enableCD'", "'enableCT'", "'stages'", "'Resource'", "'amount'", "'FeatureStoreSpec'", "'offlineStore'", "'onlineStore'", "'SecretRef'", "'OperationalPlan'", "'rationale'", "'compute'", "'packaging'", "'trainMode'", "'servingMode'", "'entryCmd'", "'featureStore'", "'secrets'", "'ciTarget'", "'baseImage'", "'dockerfile'", "'extraDeps'", "'retries'", "'cache'", "'queue'", "'NetworkSpec'", "'exposure'", "'domain'", "'tls'", "'ingressClass'", "'vpcId'", "'cidr'", "'subnets'", "'StorageTarget'", "'purpose'", "'retention'", "'encrypted'", "'SecretBackendSpec'", "'pathPrefix'", "'ObservabilitySpec'", "'stack'", "'logsEnabled'", "'metricsEnabled'", "'tracesEnabled'", "'InfraPlan'", "'provider'", "'accountId'", "'region'", "'orchestrator'", "'clusterName'", "'namespace'", "'environment'", "'runtime'", "'network'", "'storage'", "'observability'", "'spotAllowed'", "'maxMonthlyBudget'", "'piiData'", "'gdprRestricted'", "'Param'", "'Artifact'", "'path'", "'MlTask'", "'taskId'", "'type'", "'params'", "'produces'", "'consumes'", "'Edge'", "'from'", "'to'", "'MlWorkflow'", "'tasks'", "'edges'", "'+'", "'-'", "'CPU_SMALL'", "'GPU_SINGLE'", "'GPU_MULTI'", "'NONE'", "'WHEEL'", "'CONTAINER'", "'API'", "'BATCH'", "'APP'", "'PREPARE'", "'TRAIN'", "'EVAL'", "'PACKAGE'", "'REGISTER'", "'SERVE'", "'MONITOR'", "'VERSION'", "'TRACK'", "'DECIDE'", "'DEPLOY'", "'RETRAIN'", "'GOVERN'", "'NOTEBOOK'", "'SCRIPT'", "'GITHUB'", "'GITLAB'", "'AZURE'", "'JENKINS'", "'GT'", "'GE'", "'LT'", "'LE'", "'EQ'", "'STAGING'", "'PROD'", "'ARCHIVED'", "'AWS'", "'GCP'", "'ON_PREM'", "'VPS'", "'KUBERNETES'", "'ECS'", "'VM_POOL'", "'SERVERLESS_FN'", "'OBJECT_STORE'", "'BLOCK'", "'FILESYSTEM'", "'PRIVATE'", "'PUBLIC'", "'INTERNAL_ONLY'", "'AWS_SECRETS_MANAGER'", "'GCP_SECRET_MANAGER'", "'VAULT'", "'K8S_SECRET'", "'FILE_ENV'", "'PROMETHEUS'", "'CLOUDWATCH'", "'STACKDRIVER'", "'DATADOG'", "'ELK'", "'CSV'", "'PARQUET'", "'JSONL'", "'IMAGE_FOLDER'", "'VIDEO_FOLDER'", "'AUDIO_FOLDER'", "'TEXT_CORPUS'", "'HUGGINGFACE_DATASET'", "'S3'", "'GCS'", "'AZURE_BLOB'", "'BIGQUERY'", "'SQL'", "'NOSQL'", "'KAFKA'", "'REST'", "'GRAPH'", "'TABULAR'", "'IMAGE'", "'AUDIO'", "'VIDEO'", "'TEXT'", "'MULTIMODAL'", "'DATASET'", "'MODEL'", "'METRICS'", "'LOG'", "'OTHER'", "'INGEST'", "'VALIDATE_DATA'", "'VERSION_DATA'", "'TRACK_EXPERIMENT'", "'EVALUATE'", "'REGISTER_MODEL'", "'DECISION_GATE'", "'MANUAL'", "'SCHEDULED'", "'DRIFT_BASED'", "'PERFORMANCE_BASED'", "'HYBRID'", "'MLFLOW'", "'WANDB'", "'TENSORBOARD'", "'CUSTOM'", "'DVC'", "'GIT_LFS'", "'LAKEFS'", "'MLFLOW_REGISTRY'", "'AZURE_ML'", "'SAGEMAKER'", "'VERTEX_AI'"
    };
    public static final int T__144=144;
    public static final int T__265=265;
    public static final int T__143=143;
    public static final int T__264=264;
    public static final int T__146=146;
    public static final int T__267=267;
    public static final int T__50=50;
    public static final int T__145=145;
    public static final int T__266=266;
    public static final int T__140=140;
    public static final int T__261=261;
    public static final int T__260=260;
    public static final int T__142=142;
    public static final int T__263=263;
    public static final int T__141=141;
    public static final int T__262=262;
    public static final int T__59=59;
    public static final int T__55=55;
    public static final int T__56=56;
    public static final int T__57=57;
    public static final int T__58=58;
    public static final int T__51=51;
    public static final int T__137=137;
    public static final int T__258=258;
    public static final int T__52=52;
    public static final int T__136=136;
    public static final int T__257=257;
    public static final int T__53=53;
    public static final int T__139=139;
    public static final int T__54=54;
    public static final int T__138=138;
    public static final int T__259=259;
    public static final int T__133=133;
    public static final int T__254=254;
    public static final int T__132=132;
    public static final int T__253=253;
    public static final int T__60=60;
    public static final int T__135=135;
    public static final int T__256=256;
    public static final int T__61=61;
    public static final int T__134=134;
    public static final int T__255=255;
    public static final int T__250=250;
    public static final int RULE_ID=6;
    public static final int T__131=131;
    public static final int T__252=252;
    public static final int T__130=130;
    public static final int T__251=251;
    public static final int RULE_INT=5;
    public static final int T__66=66;
    public static final int T__67=67;
    public static final int T__129=129;
    public static final int T__68=68;
    public static final int T__69=69;
    public static final int T__62=62;
    public static final int T__126=126;
    public static final int T__247=247;
    public static final int T__63=63;
    public static final int T__125=125;
    public static final int T__246=246;
    public static final int T__64=64;
    public static final int T__128=128;
    public static final int T__249=249;
    public static final int T__65=65;
    public static final int T__127=127;
    public static final int T__248=248;
    public static final int T__166=166;
    public static final int T__287=287;
    public static final int T__165=165;
    public static final int T__286=286;
    public static final int T__168=168;
    public static final int T__289=289;
    public static final int T__167=167;
    public static final int T__288=288;
    public static final int T__162=162;
    public static final int T__283=283;
    public static final int T__161=161;
    public static final int T__282=282;
    public static final int T__164=164;
    public static final int T__285=285;
    public static final int T__163=163;
    public static final int T__284=284;
    public static final int T__160=160;
    public static final int T__281=281;
    public static final int T__280=280;
    public static final int T__37=37;
    public static final int T__38=38;
    public static final int T__39=39;
    public static final int T__33=33;
    public static final int T__34=34;
    public static final int T__35=35;
    public static final int T__36=36;
    public static final int T__159=159;
    public static final int T__30=30;
    public static final int T__158=158;
    public static final int T__279=279;
    public static final int T__31=31;
    public static final int T__32=32;
    public static final int T__155=155;
    public static final int T__276=276;
    public static final int T__154=154;
    public static final int T__275=275;
    public static final int T__157=157;
    public static final int T__278=278;
    public static final int T__156=156;
    public static final int T__277=277;
    public static final int T__151=151;
    public static final int T__272=272;
    public static final int T__150=150;
    public static final int T__271=271;
    public static final int T__153=153;
    public static final int T__274=274;
    public static final int T__152=152;
    public static final int T__273=273;
    public static final int T__270=270;
    public static final int T__48=48;
    public static final int T__49=49;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int RULE_FLOAT=7;
    public static final int T__46=46;
    public static final int T__47=47;
    public static final int T__40=40;
    public static final int T__148=148;
    public static final int T__269=269;
    public static final int T__41=41;
    public static final int T__147=147;
    public static final int T__268=268;
    public static final int T__42=42;
    public static final int T__43=43;
    public static final int T__149=149;
    public static final int T__100=100;
    public static final int T__221=221;
    public static final int T__342=342;
    public static final int T__220=220;
    public static final int T__341=341;
    public static final int T__102=102;
    public static final int T__223=223;
    public static final int T__344=344;
    public static final int T__101=101;
    public static final int T__222=222;
    public static final int T__343=343;
    public static final int T__340=340;
    public static final int T__19=19;
    public static final int T__15=15;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__218=218;
    public static final int T__339=339;
    public static final int T__12=12;
    public static final int T__217=217;
    public static final int T__338=338;
    public static final int T__13=13;
    public static final int T__14=14;
    public static final int T__219=219;
    public static final int T__214=214;
    public static final int T__335=335;
    public static final int T__213=213;
    public static final int T__334=334;
    public static final int T__216=216;
    public static final int T__337=337;
    public static final int T__215=215;
    public static final int T__336=336;
    public static final int T__210=210;
    public static final int T__331=331;
    public static final int T__330=330;
    public static final int T__212=212;
    public static final int T__333=333;
    public static final int T__211=211;
    public static final int T__332=332;
    public static final int T__26=26;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int T__29=29;
    public static final int T__22=22;
    public static final int T__207=207;
    public static final int T__328=328;
    public static final int T__23=23;
    public static final int T__206=206;
    public static final int T__327=327;
    public static final int T__24=24;
    public static final int T__209=209;
    public static final int T__25=25;
    public static final int T__208=208;
    public static final int T__329=329;
    public static final int T__203=203;
    public static final int T__324=324;
    public static final int T__202=202;
    public static final int T__323=323;
    public static final int T__20=20;
    public static final int T__205=205;
    public static final int T__326=326;
    public static final int T__21=21;
    public static final int T__204=204;
    public static final int T__325=325;
    public static final int T__122=122;
    public static final int T__243=243;
    public static final int T__121=121;
    public static final int T__242=242;
    public static final int T__124=124;
    public static final int T__245=245;
    public static final int T__123=123;
    public static final int T__244=244;
    public static final int T__120=120;
    public static final int T__241=241;
    public static final int T__240=240;
    public static final int RULE_SL_COMMENT=9;
    public static final int T__119=119;
    public static final int T__118=118;
    public static final int T__239=239;
    public static final int T__115=115;
    public static final int T__236=236;
    public static final int T__357=357;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__235=235;
    public static final int T__356=356;
    public static final int T__117=117;
    public static final int T__238=238;
    public static final int T__116=116;
    public static final int T__237=237;
    public static final int T__111=111;
    public static final int T__232=232;
    public static final int T__353=353;
    public static final int T__110=110;
    public static final int T__231=231;
    public static final int T__352=352;
    public static final int T__113=113;
    public static final int T__234=234;
    public static final int T__355=355;
    public static final int T__112=112;
    public static final int T__233=233;
    public static final int T__354=354;
    public static final int T__230=230;
    public static final int T__351=351;
    public static final int T__350=350;
    public static final int T__108=108;
    public static final int T__229=229;
    public static final int T__107=107;
    public static final int T__228=228;
    public static final int T__349=349;
    public static final int T__109=109;
    public static final int T__104=104;
    public static final int T__225=225;
    public static final int T__346=346;
    public static final int T__103=103;
    public static final int T__224=224;
    public static final int T__345=345;
    public static final int T__106=106;
    public static final int T__227=227;
    public static final int T__348=348;
    public static final int T__105=105;
    public static final int T__226=226;
    public static final int T__347=347;
    public static final int T__300=300;
    public static final int RULE_ML_COMMENT=8;
    public static final int T__320=320;
    public static final int T__201=201;
    public static final int T__322=322;
    public static final int T__200=200;
    public static final int T__321=321;
    public static final int T__317=317;
    public static final int T__316=316;
    public static final int T__319=319;
    public static final int T__318=318;
    public static final int T__313=313;
    public static final int T__312=312;
    public static final int T__315=315;
    public static final int T__314=314;
    public static final int T__311=311;
    public static final int T__310=310;
    public static final int T__309=309;
    public static final int T__306=306;
    public static final int T__305=305;
    public static final int T__308=308;
    public static final int T__307=307;
    public static final int T__302=302;
    public static final int T__301=301;
    public static final int T__304=304;
    public static final int T__303=303;
    public static final int T__91=91;
    public static final int T__188=188;
    public static final int T__92=92;
    public static final int T__187=187;
    public static final int T__93=93;
    public static final int T__94=94;
    public static final int T__189=189;
    public static final int T__184=184;
    public static final int T__183=183;
    public static final int T__186=186;
    public static final int T__90=90;
    public static final int T__185=185;
    public static final int T__180=180;
    public static final int T__182=182;
    public static final int T__181=181;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int T__98=98;
    public static final int T__177=177;
    public static final int T__298=298;
    public static final int T__176=176;
    public static final int T__297=297;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int T__299=299;
    public static final int T__173=173;
    public static final int T__294=294;
    public static final int T__172=172;
    public static final int T__293=293;
    public static final int T__175=175;
    public static final int T__296=296;
    public static final int T__174=174;
    public static final int T__295=295;
    public static final int T__290=290;
    public static final int T__171=171;
    public static final int T__292=292;
    public static final int T__170=170;
    public static final int T__291=291;
    public static final int T__169=169;
    public static final int T__70=70;
    public static final int T__71=71;
    public static final int T__72=72;
    public static final int RULE_STRING=4;
    public static final int T__77=77;
    public static final int T__78=78;
    public static final int T__79=79;
    public static final int T__73=73;
    public static final int T__74=74;
    public static final int T__75=75;
    public static final int T__76=76;
    public static final int T__80=80;
    public static final int T__199=199;
    public static final int T__81=81;
    public static final int T__198=198;
    public static final int T__82=82;
    public static final int T__83=83;
    public static final int T__195=195;
    public static final int T__194=194;
    public static final int RULE_WS=10;
    public static final int T__197=197;
    public static final int T__196=196;
    public static final int T__191=191;
    public static final int T__190=190;
    public static final int T__193=193;
    public static final int T__192=192;
    public static final int RULE_ANY_OTHER=11;
    public static final int T__88=88;
    public static final int T__89=89;
    public static final int T__84=84;
    public static final int T__85=85;
    public static final int T__86=86;
    public static final int T__87=87;

    // delegates
    // delegators


        public InternalMlopsDslParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public InternalMlopsDslParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return InternalMlopsDslParser.tokenNames; }
    public String getGrammarFileName() { return "InternalMlopsDsl.g"; }



     	private MlopsDslGrammarAccess grammarAccess;

        public InternalMlopsDslParser(TokenStream input, MlopsDslGrammarAccess grammarAccess) {
            this(input);
            this.grammarAccess = grammarAccess;
            registerRules(grammarAccess.getGrammar());
        }

        @Override
        protected String getFirstRuleName() {
        	return "Script";
       	}

       	@Override
       	protected MlopsDslGrammarAccess getGrammarAccess() {
       		return grammarAccess;
       	}




    // $ANTLR start "entryRuleScript"
    // InternalMlopsDsl.g:65:1: entryRuleScript returns [EObject current=null] : iv_ruleScript= ruleScript EOF ;
    public final EObject entryRuleScript() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleScript = null;


        try {
            // InternalMlopsDsl.g:65:47: (iv_ruleScript= ruleScript EOF )
            // InternalMlopsDsl.g:66:2: iv_ruleScript= ruleScript EOF
            {
             newCompositeNode(grammarAccess.getScriptRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleScript=ruleScript();

            state._fsp--;

             current =iv_ruleScript; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleScript"


    // $ANTLR start "ruleScript"
    // InternalMlopsDsl.g:72:1: ruleScript returns [EObject current=null] : ( (lv_elements_0_0= ruleModel ) )+ ;
    public final EObject ruleScript() throws RecognitionException {
        EObject current = null;

        EObject lv_elements_0_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:78:2: ( ( (lv_elements_0_0= ruleModel ) )+ )
            // InternalMlopsDsl.g:79:2: ( (lv_elements_0_0= ruleModel ) )+
            {
            // InternalMlopsDsl.g:79:2: ( (lv_elements_0_0= ruleModel ) )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==12||LA1_0==17||LA1_0==24||LA1_0==33||LA1_0==38||LA1_0==47||LA1_0==62||(LA1_0>=80 && LA1_0<=81)||LA1_0==92||LA1_0==95||LA1_0==100||LA1_0==111||LA1_0==116||LA1_0==124||LA1_0==130||LA1_0==141||LA1_0==150||LA1_0==157||LA1_0==166||LA1_0==175||LA1_0==179||LA1_0==214||LA1_0==242) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // InternalMlopsDsl.g:80:3: (lv_elements_0_0= ruleModel )
            	    {
            	    // InternalMlopsDsl.g:80:3: (lv_elements_0_0= ruleModel )
            	    // InternalMlopsDsl.g:81:4: lv_elements_0_0= ruleModel
            	    {

            	    				newCompositeNode(grammarAccess.getScriptAccess().getElementsModelParserRuleCall_0());
            	    			
            	    pushFollow(FOLLOW_3);
            	    lv_elements_0_0=ruleModel();

            	    state._fsp--;


            	    				if (current==null) {
            	    					current = createModelElementForParent(grammarAccess.getScriptRule());
            	    				}
            	    				add(
            	    					current,
            	    					"elements",
            	    					lv_elements_0_0,
            	    					"org.xtext.ucd.mlopsdsl.MlopsDsl.Model");
            	    				afterParserOrEnumRuleCall();
            	    			

            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleScript"


    // $ANTLR start "entryRuleEnvironmentSpec"
    // InternalMlopsDsl.g:101:1: entryRuleEnvironmentSpec returns [EObject current=null] : iv_ruleEnvironmentSpec= ruleEnvironmentSpec EOF ;
    public final EObject entryRuleEnvironmentSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEnvironmentSpec = null;


        try {
            // InternalMlopsDsl.g:101:56: (iv_ruleEnvironmentSpec= ruleEnvironmentSpec EOF )
            // InternalMlopsDsl.g:102:2: iv_ruleEnvironmentSpec= ruleEnvironmentSpec EOF
            {
             newCompositeNode(grammarAccess.getEnvironmentSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEnvironmentSpec=ruleEnvironmentSpec();

            state._fsp--;

             current =iv_ruleEnvironmentSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEnvironmentSpec"


    // $ANTLR start "ruleEnvironmentSpec"
    // InternalMlopsDsl.g:108:1: ruleEnvironmentSpec returns [EObject current=null] : (otherlv_0= 'EnvironmentSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' ) ;
    public final EObject ruleEnvironmentSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:114:2: ( (otherlv_0= 'EnvironmentSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' ) )
            // InternalMlopsDsl.g:115:2: (otherlv_0= 'EnvironmentSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' )
            {
            // InternalMlopsDsl.g:115:2: (otherlv_0= 'EnvironmentSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' )
            // InternalMlopsDsl.g:116:3: otherlv_0= 'EnvironmentSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,12,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getEnvironmentSpecAccess().getEnvironmentSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getEnvironmentSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getEnvironmentSpecAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getEnvironmentSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:132:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:133:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:133:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:134:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_name_4_0, grammarAccess.getEnvironmentSpecAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEnvironmentSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getEnvironmentSpecAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEnvironmentSpec"


    // $ANTLR start "entryRuleTerraformBackendSpec"
    // InternalMlopsDsl.g:158:1: entryRuleTerraformBackendSpec returns [EObject current=null] : iv_ruleTerraformBackendSpec= ruleTerraformBackendSpec EOF ;
    public final EObject entryRuleTerraformBackendSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTerraformBackendSpec = null;


        try {
            // InternalMlopsDsl.g:158:61: (iv_ruleTerraformBackendSpec= ruleTerraformBackendSpec EOF )
            // InternalMlopsDsl.g:159:2: iv_ruleTerraformBackendSpec= ruleTerraformBackendSpec EOF
            {
             newCompositeNode(grammarAccess.getTerraformBackendSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTerraformBackendSpec=ruleTerraformBackendSpec();

            state._fsp--;

             current =iv_ruleTerraformBackendSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTerraformBackendSpec"


    // $ANTLR start "ruleTerraformBackendSpec"
    // InternalMlopsDsl.g:165:1: ruleTerraformBackendSpec returns [EObject current=null] : (otherlv_0= 'TerraformBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= RULE_STRING ) ) (otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) ) )? otherlv_8= 'key' otherlv_9= '=' ( (lv_key_10_0= RULE_STRING ) ) (otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) ) )? (otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) ) )? (otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) ) )? otherlv_20= '}' ) ;
    public final EObject ruleTerraformBackendSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_kind_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_bucket_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_key_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_resourceGroup_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_storageAccount_16_0=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token lv_container_19_0=null;
        Token otherlv_20=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:171:2: ( (otherlv_0= 'TerraformBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= RULE_STRING ) ) (otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) ) )? otherlv_8= 'key' otherlv_9= '=' ( (lv_key_10_0= RULE_STRING ) ) (otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) ) )? (otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) ) )? (otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) ) )? otherlv_20= '}' ) )
            // InternalMlopsDsl.g:172:2: (otherlv_0= 'TerraformBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= RULE_STRING ) ) (otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) ) )? otherlv_8= 'key' otherlv_9= '=' ( (lv_key_10_0= RULE_STRING ) ) (otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) ) )? (otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) ) )? (otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) ) )? otherlv_20= '}' )
            {
            // InternalMlopsDsl.g:172:2: (otherlv_0= 'TerraformBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= RULE_STRING ) ) (otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) ) )? otherlv_8= 'key' otherlv_9= '=' ( (lv_key_10_0= RULE_STRING ) ) (otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) ) )? (otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) ) )? (otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) ) )? otherlv_20= '}' )
            // InternalMlopsDsl.g:173:3: otherlv_0= 'TerraformBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= RULE_STRING ) ) (otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) ) )? otherlv_8= 'key' otherlv_9= '=' ( (lv_key_10_0= RULE_STRING ) ) (otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) ) )? (otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) ) )? (otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) ) )? otherlv_20= '}'
            {
            otherlv_0=(Token)match(input,17,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getTerraformBackendSpecAccess().getTerraformBackendSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getTerraformBackendSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getTerraformBackendSpecAccess().getKindKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getTerraformBackendSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:189:3: ( (lv_kind_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:190:4: (lv_kind_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:190:4: (lv_kind_4_0= RULE_STRING )
            // InternalMlopsDsl.g:191:5: lv_kind_4_0= RULE_STRING
            {
            lv_kind_4_0=(Token)match(input,RULE_STRING,FOLLOW_10); 

            					newLeafNode(lv_kind_4_0, grammarAccess.getTerraformBackendSpecAccess().getKindSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTerraformBackendSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"kind",
            						lv_kind_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:207:3: (otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) ) )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==19) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // InternalMlopsDsl.g:208:4: otherlv_5= 'bucket' otherlv_6= '=' ( (lv_bucket_7_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,19,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getTerraformBackendSpecAccess().getBucketKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_6, grammarAccess.getTerraformBackendSpecAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:216:4: ( (lv_bucket_7_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:217:5: (lv_bucket_7_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:217:5: (lv_bucket_7_0= RULE_STRING )
                    // InternalMlopsDsl.g:218:6: lv_bucket_7_0= RULE_STRING
                    {
                    lv_bucket_7_0=(Token)match(input,RULE_STRING,FOLLOW_11); 

                    						newLeafNode(lv_bucket_7_0, grammarAccess.getTerraformBackendSpecAccess().getBucketSTRINGTerminalRuleCall_5_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTerraformBackendSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"bucket",
                    							lv_bucket_7_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,20,FOLLOW_6); 

            			newLeafNode(otherlv_8, grammarAccess.getTerraformBackendSpecAccess().getKeyKeyword_6());
            		
            otherlv_9=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_9, grammarAccess.getTerraformBackendSpecAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:243:3: ( (lv_key_10_0= RULE_STRING ) )
            // InternalMlopsDsl.g:244:4: (lv_key_10_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:244:4: (lv_key_10_0= RULE_STRING )
            // InternalMlopsDsl.g:245:5: lv_key_10_0= RULE_STRING
            {
            lv_key_10_0=(Token)match(input,RULE_STRING,FOLLOW_12); 

            					newLeafNode(lv_key_10_0, grammarAccess.getTerraformBackendSpecAccess().getKeySTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTerraformBackendSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"key",
            						lv_key_10_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:261:3: (otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) ) )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==21) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // InternalMlopsDsl.g:262:4: otherlv_11= 'resourceGroup' otherlv_12= '=' ( (lv_resourceGroup_13_0= RULE_STRING ) )
                    {
                    otherlv_11=(Token)match(input,21,FOLLOW_6); 

                    				newLeafNode(otherlv_11, grammarAccess.getTerraformBackendSpecAccess().getResourceGroupKeyword_9_0());
                    			
                    otherlv_12=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_12, grammarAccess.getTerraformBackendSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:270:4: ( (lv_resourceGroup_13_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:271:5: (lv_resourceGroup_13_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:271:5: (lv_resourceGroup_13_0= RULE_STRING )
                    // InternalMlopsDsl.g:272:6: lv_resourceGroup_13_0= RULE_STRING
                    {
                    lv_resourceGroup_13_0=(Token)match(input,RULE_STRING,FOLLOW_13); 

                    						newLeafNode(lv_resourceGroup_13_0, grammarAccess.getTerraformBackendSpecAccess().getResourceGroupSTRINGTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTerraformBackendSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"resourceGroup",
                    							lv_resourceGroup_13_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:289:3: (otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) ) )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==22) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // InternalMlopsDsl.g:290:4: otherlv_14= 'storageAccount' otherlv_15= '=' ( (lv_storageAccount_16_0= RULE_STRING ) )
                    {
                    otherlv_14=(Token)match(input,22,FOLLOW_6); 

                    				newLeafNode(otherlv_14, grammarAccess.getTerraformBackendSpecAccess().getStorageAccountKeyword_10_0());
                    			
                    otherlv_15=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_15, grammarAccess.getTerraformBackendSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:298:4: ( (lv_storageAccount_16_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:299:5: (lv_storageAccount_16_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:299:5: (lv_storageAccount_16_0= RULE_STRING )
                    // InternalMlopsDsl.g:300:6: lv_storageAccount_16_0= RULE_STRING
                    {
                    lv_storageAccount_16_0=(Token)match(input,RULE_STRING,FOLLOW_14); 

                    						newLeafNode(lv_storageAccount_16_0, grammarAccess.getTerraformBackendSpecAccess().getStorageAccountSTRINGTerminalRuleCall_10_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTerraformBackendSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"storageAccount",
                    							lv_storageAccount_16_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:317:3: (otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) ) )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==23) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // InternalMlopsDsl.g:318:4: otherlv_17= 'container' otherlv_18= '=' ( (lv_container_19_0= RULE_STRING ) )
                    {
                    otherlv_17=(Token)match(input,23,FOLLOW_6); 

                    				newLeafNode(otherlv_17, grammarAccess.getTerraformBackendSpecAccess().getContainerKeyword_11_0());
                    			
                    otherlv_18=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_18, grammarAccess.getTerraformBackendSpecAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:326:4: ( (lv_container_19_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:327:5: (lv_container_19_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:327:5: (lv_container_19_0= RULE_STRING )
                    // InternalMlopsDsl.g:328:6: lv_container_19_0= RULE_STRING
                    {
                    lv_container_19_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_container_19_0, grammarAccess.getTerraformBackendSpecAccess().getContainerSTRINGTerminalRuleCall_11_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTerraformBackendSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"container",
                    							lv_container_19_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_20=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_20, grammarAccess.getTerraformBackendSpecAccess().getRightCurlyBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTerraformBackendSpec"


    // $ANTLR start "entryRuleNodePoolSpec"
    // InternalMlopsDsl.g:353:1: entryRuleNodePoolSpec returns [EObject current=null] : iv_ruleNodePoolSpec= ruleNodePoolSpec EOF ;
    public final EObject entryRuleNodePoolSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNodePoolSpec = null;


        try {
            // InternalMlopsDsl.g:353:53: (iv_ruleNodePoolSpec= ruleNodePoolSpec EOF )
            // InternalMlopsDsl.g:354:2: iv_ruleNodePoolSpec= ruleNodePoolSpec EOF
            {
             newCompositeNode(grammarAccess.getNodePoolSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNodePoolSpec=ruleNodePoolSpec();

            state._fsp--;

             current =iv_ruleNodePoolSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNodePoolSpec"


    // $ANTLR start "ruleNodePoolSpec"
    // InternalMlopsDsl.g:360:1: ruleNodePoolSpec returns [EObject current=null] : (otherlv_0= 'NodePoolSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'machineType' otherlv_6= '=' ( (lv_machineType_7_0= RULE_STRING ) ) otherlv_8= 'minNodes' otherlv_9= '=' ( (lv_minNodes_10_0= RULE_INT ) ) otherlv_11= 'desiredNodes' otherlv_12= '=' ( (lv_desiredNodes_13_0= RULE_INT ) ) otherlv_14= 'maxNodes' otherlv_15= '=' ( (lv_maxNodes_16_0= RULE_INT ) ) (otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) ) )? (otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) ) )? otherlv_23= '}' ) ;
    public final EObject ruleNodePoolSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_machineType_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_minNodes_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_desiredNodes_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_maxNodes_16_0=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        AntlrDatatypeRuleToken lv_gpu_19_0 = null;

        AntlrDatatypeRuleToken lv_spot_22_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:366:2: ( (otherlv_0= 'NodePoolSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'machineType' otherlv_6= '=' ( (lv_machineType_7_0= RULE_STRING ) ) otherlv_8= 'minNodes' otherlv_9= '=' ( (lv_minNodes_10_0= RULE_INT ) ) otherlv_11= 'desiredNodes' otherlv_12= '=' ( (lv_desiredNodes_13_0= RULE_INT ) ) otherlv_14= 'maxNodes' otherlv_15= '=' ( (lv_maxNodes_16_0= RULE_INT ) ) (otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) ) )? (otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) ) )? otherlv_23= '}' ) )
            // InternalMlopsDsl.g:367:2: (otherlv_0= 'NodePoolSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'machineType' otherlv_6= '=' ( (lv_machineType_7_0= RULE_STRING ) ) otherlv_8= 'minNodes' otherlv_9= '=' ( (lv_minNodes_10_0= RULE_INT ) ) otherlv_11= 'desiredNodes' otherlv_12= '=' ( (lv_desiredNodes_13_0= RULE_INT ) ) otherlv_14= 'maxNodes' otherlv_15= '=' ( (lv_maxNodes_16_0= RULE_INT ) ) (otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) ) )? (otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) ) )? otherlv_23= '}' )
            {
            // InternalMlopsDsl.g:367:2: (otherlv_0= 'NodePoolSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'machineType' otherlv_6= '=' ( (lv_machineType_7_0= RULE_STRING ) ) otherlv_8= 'minNodes' otherlv_9= '=' ( (lv_minNodes_10_0= RULE_INT ) ) otherlv_11= 'desiredNodes' otherlv_12= '=' ( (lv_desiredNodes_13_0= RULE_INT ) ) otherlv_14= 'maxNodes' otherlv_15= '=' ( (lv_maxNodes_16_0= RULE_INT ) ) (otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) ) )? (otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) ) )? otherlv_23= '}' )
            // InternalMlopsDsl.g:368:3: otherlv_0= 'NodePoolSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'machineType' otherlv_6= '=' ( (lv_machineType_7_0= RULE_STRING ) ) otherlv_8= 'minNodes' otherlv_9= '=' ( (lv_minNodes_10_0= RULE_INT ) ) otherlv_11= 'desiredNodes' otherlv_12= '=' ( (lv_desiredNodes_13_0= RULE_INT ) ) otherlv_14= 'maxNodes' otherlv_15= '=' ( (lv_maxNodes_16_0= RULE_INT ) ) (otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) ) )? (otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) ) )? otherlv_23= '}'
            {
            otherlv_0=(Token)match(input,24,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getNodePoolSpecAccess().getNodePoolSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getNodePoolSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getNodePoolSpecAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:384:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:385:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:385:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:386:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_15); 

            					newLeafNode(lv_name_4_0, grammarAccess.getNodePoolSpecAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodePoolSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,25,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getNodePoolSpecAccess().getMachineTypeKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_6, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:410:3: ( (lv_machineType_7_0= RULE_STRING ) )
            // InternalMlopsDsl.g:411:4: (lv_machineType_7_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:411:4: (lv_machineType_7_0= RULE_STRING )
            // InternalMlopsDsl.g:412:5: lv_machineType_7_0= RULE_STRING
            {
            lv_machineType_7_0=(Token)match(input,RULE_STRING,FOLLOW_16); 

            					newLeafNode(lv_machineType_7_0, grammarAccess.getNodePoolSpecAccess().getMachineTypeSTRINGTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodePoolSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"machineType",
            						lv_machineType_7_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_8=(Token)match(input,26,FOLLOW_6); 

            			newLeafNode(otherlv_8, grammarAccess.getNodePoolSpecAccess().getMinNodesKeyword_8());
            		
            otherlv_9=(Token)match(input,15,FOLLOW_17); 

            			newLeafNode(otherlv_9, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_9());
            		
            // InternalMlopsDsl.g:436:3: ( (lv_minNodes_10_0= RULE_INT ) )
            // InternalMlopsDsl.g:437:4: (lv_minNodes_10_0= RULE_INT )
            {
            // InternalMlopsDsl.g:437:4: (lv_minNodes_10_0= RULE_INT )
            // InternalMlopsDsl.g:438:5: lv_minNodes_10_0= RULE_INT
            {
            lv_minNodes_10_0=(Token)match(input,RULE_INT,FOLLOW_18); 

            					newLeafNode(lv_minNodes_10_0, grammarAccess.getNodePoolSpecAccess().getMinNodesINTTerminalRuleCall_10_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodePoolSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"minNodes",
            						lv_minNodes_10_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_11=(Token)match(input,27,FOLLOW_6); 

            			newLeafNode(otherlv_11, grammarAccess.getNodePoolSpecAccess().getDesiredNodesKeyword_11());
            		
            otherlv_12=(Token)match(input,15,FOLLOW_17); 

            			newLeafNode(otherlv_12, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_12());
            		
            // InternalMlopsDsl.g:462:3: ( (lv_desiredNodes_13_0= RULE_INT ) )
            // InternalMlopsDsl.g:463:4: (lv_desiredNodes_13_0= RULE_INT )
            {
            // InternalMlopsDsl.g:463:4: (lv_desiredNodes_13_0= RULE_INT )
            // InternalMlopsDsl.g:464:5: lv_desiredNodes_13_0= RULE_INT
            {
            lv_desiredNodes_13_0=(Token)match(input,RULE_INT,FOLLOW_19); 

            					newLeafNode(lv_desiredNodes_13_0, grammarAccess.getNodePoolSpecAccess().getDesiredNodesINTTerminalRuleCall_13_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodePoolSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"desiredNodes",
            						lv_desiredNodes_13_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_14=(Token)match(input,28,FOLLOW_6); 

            			newLeafNode(otherlv_14, grammarAccess.getNodePoolSpecAccess().getMaxNodesKeyword_14());
            		
            otherlv_15=(Token)match(input,15,FOLLOW_17); 

            			newLeafNode(otherlv_15, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_15());
            		
            // InternalMlopsDsl.g:488:3: ( (lv_maxNodes_16_0= RULE_INT ) )
            // InternalMlopsDsl.g:489:4: (lv_maxNodes_16_0= RULE_INT )
            {
            // InternalMlopsDsl.g:489:4: (lv_maxNodes_16_0= RULE_INT )
            // InternalMlopsDsl.g:490:5: lv_maxNodes_16_0= RULE_INT
            {
            lv_maxNodes_16_0=(Token)match(input,RULE_INT,FOLLOW_20); 

            					newLeafNode(lv_maxNodes_16_0, grammarAccess.getNodePoolSpecAccess().getMaxNodesINTTerminalRuleCall_16_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getNodePoolSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"maxNodes",
            						lv_maxNodes_16_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            // InternalMlopsDsl.g:506:3: (otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) ) )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==29) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // InternalMlopsDsl.g:507:4: otherlv_17= 'gpu' otherlv_18= '=' ( (lv_gpu_19_0= ruleBooleanValue ) )
                    {
                    otherlv_17=(Token)match(input,29,FOLLOW_6); 

                    				newLeafNode(otherlv_17, grammarAccess.getNodePoolSpecAccess().getGpuKeyword_17_0());
                    			
                    otherlv_18=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_18, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_17_1());
                    			
                    // InternalMlopsDsl.g:515:4: ( (lv_gpu_19_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:516:5: (lv_gpu_19_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:516:5: (lv_gpu_19_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:517:6: lv_gpu_19_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getNodePoolSpecAccess().getGpuBooleanValueParserRuleCall_17_2_0());
                    					
                    pushFollow(FOLLOW_22);
                    lv_gpu_19_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getNodePoolSpecRule());
                    						}
                    						set(
                    							current,
                    							"gpu",
                    							lv_gpu_19_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:535:3: (otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) ) )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==30) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // InternalMlopsDsl.g:536:4: otherlv_20= 'spot' otherlv_21= '=' ( (lv_spot_22_0= ruleBooleanValue ) )
                    {
                    otherlv_20=(Token)match(input,30,FOLLOW_6); 

                    				newLeafNode(otherlv_20, grammarAccess.getNodePoolSpecAccess().getSpotKeyword_18_0());
                    			
                    otherlv_21=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_21, grammarAccess.getNodePoolSpecAccess().getEqualsSignKeyword_18_1());
                    			
                    // InternalMlopsDsl.g:544:4: ( (lv_spot_22_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:545:5: (lv_spot_22_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:545:5: (lv_spot_22_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:546:6: lv_spot_22_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getNodePoolSpecAccess().getSpotBooleanValueParserRuleCall_18_2_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_spot_22_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getNodePoolSpecRule());
                    						}
                    						set(
                    							current,
                    							"spot",
                    							lv_spot_22_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_23=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_23, grammarAccess.getNodePoolSpecAccess().getRightCurlyBracketKeyword_19());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNodePoolSpec"


    // $ANTLR start "entryRuleBooleanValue"
    // InternalMlopsDsl.g:572:1: entryRuleBooleanValue returns [String current=null] : iv_ruleBooleanValue= ruleBooleanValue EOF ;
    public final String entryRuleBooleanValue() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleBooleanValue = null;


        try {
            // InternalMlopsDsl.g:572:52: (iv_ruleBooleanValue= ruleBooleanValue EOF )
            // InternalMlopsDsl.g:573:2: iv_ruleBooleanValue= ruleBooleanValue EOF
            {
             newCompositeNode(grammarAccess.getBooleanValueRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleBooleanValue=ruleBooleanValue();

            state._fsp--;

             current =iv_ruleBooleanValue.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleBooleanValue"


    // $ANTLR start "ruleBooleanValue"
    // InternalMlopsDsl.g:579:1: ruleBooleanValue returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : (kw= 'true' | kw= 'false' ) ;
    public final AntlrDatatypeRuleToken ruleBooleanValue() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:585:2: ( (kw= 'true' | kw= 'false' ) )
            // InternalMlopsDsl.g:586:2: (kw= 'true' | kw= 'false' )
            {
            // InternalMlopsDsl.g:586:2: (kw= 'true' | kw= 'false' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==31) ) {
                alt8=1;
            }
            else if ( (LA8_0==32) ) {
                alt8=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // InternalMlopsDsl.g:587:3: kw= 'true'
                    {
                    kw=(Token)match(input,31,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBooleanValueAccess().getTrueKeyword_0());
                    		

                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:593:3: kw= 'false'
                    {
                    kw=(Token)match(input,32,FOLLOW_2); 

                    			current.merge(kw);
                    			newLeafNode(kw, grammarAccess.getBooleanValueAccess().getFalseKeyword_1());
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleBooleanValue"


    // $ANTLR start "entryRuleRuntimeSpec"
    // InternalMlopsDsl.g:602:1: entryRuleRuntimeSpec returns [EObject current=null] : iv_ruleRuntimeSpec= ruleRuntimeSpec EOF ;
    public final EObject entryRuleRuntimeSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRuntimeSpec = null;


        try {
            // InternalMlopsDsl.g:602:52: (iv_ruleRuntimeSpec= ruleRuntimeSpec EOF )
            // InternalMlopsDsl.g:603:2: iv_ruleRuntimeSpec= ruleRuntimeSpec EOF
            {
             newCompositeNode(grammarAccess.getRuntimeSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRuntimeSpec=ruleRuntimeSpec();

            state._fsp--;

             current =iv_ruleRuntimeSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRuntimeSpec"


    // $ANTLR start "ruleRuntimeSpec"
    // InternalMlopsDsl.g:609:1: ruleRuntimeSpec returns [EObject current=null] : (otherlv_0= 'RuntimeSpec' otherlv_1= '{' otherlv_2= 'nodePools' otherlv_3= '=' otherlv_4= '[' ( (lv_nodePools_5_0= ruleNodePoolSpec ) ) (otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) ) )* otherlv_8= ']' otherlv_9= '}' ) ;
    public final EObject ruleRuntimeSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        EObject lv_nodePools_5_0 = null;

        EObject lv_nodePools_7_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:615:2: ( (otherlv_0= 'RuntimeSpec' otherlv_1= '{' otherlv_2= 'nodePools' otherlv_3= '=' otherlv_4= '[' ( (lv_nodePools_5_0= ruleNodePoolSpec ) ) (otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) ) )* otherlv_8= ']' otherlv_9= '}' ) )
            // InternalMlopsDsl.g:616:2: (otherlv_0= 'RuntimeSpec' otherlv_1= '{' otherlv_2= 'nodePools' otherlv_3= '=' otherlv_4= '[' ( (lv_nodePools_5_0= ruleNodePoolSpec ) ) (otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) ) )* otherlv_8= ']' otherlv_9= '}' )
            {
            // InternalMlopsDsl.g:616:2: (otherlv_0= 'RuntimeSpec' otherlv_1= '{' otherlv_2= 'nodePools' otherlv_3= '=' otherlv_4= '[' ( (lv_nodePools_5_0= ruleNodePoolSpec ) ) (otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) ) )* otherlv_8= ']' otherlv_9= '}' )
            // InternalMlopsDsl.g:617:3: otherlv_0= 'RuntimeSpec' otherlv_1= '{' otherlv_2= 'nodePools' otherlv_3= '=' otherlv_4= '[' ( (lv_nodePools_5_0= ruleNodePoolSpec ) ) (otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) ) )* otherlv_8= ']' otherlv_9= '}'
            {
            otherlv_0=(Token)match(input,33,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getRuntimeSpecAccess().getRuntimeSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_23); 

            			newLeafNode(otherlv_1, grammarAccess.getRuntimeSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,34,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getRuntimeSpecAccess().getNodePoolsKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_3, grammarAccess.getRuntimeSpecAccess().getEqualsSignKeyword_3());
            		
            otherlv_4=(Token)match(input,35,FOLLOW_25); 

            			newLeafNode(otherlv_4, grammarAccess.getRuntimeSpecAccess().getLeftSquareBracketKeyword_4());
            		
            // InternalMlopsDsl.g:637:3: ( (lv_nodePools_5_0= ruleNodePoolSpec ) )
            // InternalMlopsDsl.g:638:4: (lv_nodePools_5_0= ruleNodePoolSpec )
            {
            // InternalMlopsDsl.g:638:4: (lv_nodePools_5_0= ruleNodePoolSpec )
            // InternalMlopsDsl.g:639:5: lv_nodePools_5_0= ruleNodePoolSpec
            {

            					newCompositeNode(grammarAccess.getRuntimeSpecAccess().getNodePoolsNodePoolSpecParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_26);
            lv_nodePools_5_0=ruleNodePoolSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRuntimeSpecRule());
            					}
            					add(
            						current,
            						"nodePools",
            						lv_nodePools_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.NodePoolSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:656:3: (otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) ) )*
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==36) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // InternalMlopsDsl.g:657:4: otherlv_6= ',' ( (lv_nodePools_7_0= ruleNodePoolSpec ) )
            	    {
            	    otherlv_6=(Token)match(input,36,FOLLOW_25); 

            	    				newLeafNode(otherlv_6, grammarAccess.getRuntimeSpecAccess().getCommaKeyword_6_0());
            	    			
            	    // InternalMlopsDsl.g:661:4: ( (lv_nodePools_7_0= ruleNodePoolSpec ) )
            	    // InternalMlopsDsl.g:662:5: (lv_nodePools_7_0= ruleNodePoolSpec )
            	    {
            	    // InternalMlopsDsl.g:662:5: (lv_nodePools_7_0= ruleNodePoolSpec )
            	    // InternalMlopsDsl.g:663:6: lv_nodePools_7_0= ruleNodePoolSpec
            	    {

            	    						newCompositeNode(grammarAccess.getRuntimeSpecAccess().getNodePoolsNodePoolSpecParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_nodePools_7_0=ruleNodePoolSpec();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getRuntimeSpecRule());
            	    						}
            	    						add(
            	    							current,
            	    							"nodePools",
            	    							lv_nodePools_7_0,
            	    							"org.xtext.ucd.mlopsdsl.MlopsDsl.NodePoolSpec");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop9;
                }
            } while (true);

            otherlv_8=(Token)match(input,37,FOLLOW_8); 

            			newLeafNode(otherlv_8, grammarAccess.getRuntimeSpecAccess().getRightSquareBracketKeyword_7());
            		
            otherlv_9=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_9, grammarAccess.getRuntimeSpecAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRuntimeSpec"


    // $ANTLR start "entryRuleModel"
    // InternalMlopsDsl.g:693:1: entryRuleModel returns [EObject current=null] : iv_ruleModel= ruleModel EOF ;
    public final EObject entryRuleModel() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModel = null;


        try {
            // InternalMlopsDsl.g:693:46: (iv_ruleModel= ruleModel EOF )
            // InternalMlopsDsl.g:694:2: iv_ruleModel= ruleModel EOF
            {
             newCompositeNode(grammarAccess.getModelRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModel=ruleModel();

            state._fsp--;

             current =iv_ruleModel; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModel"


    // $ANTLR start "ruleModel"
    // InternalMlopsDsl.g:700:1: ruleModel returns [EObject current=null] : (this_MetaModelSpec_0= ruleMetaModelSpec | this_ProjectSpec_1= ruleProjectSpec | this_DataAsset_2= ruleDataAsset | this_FeatureSet_3= ruleFeatureSet | this_TrainingSpec_4= ruleTrainingSpec | this_EvaluationSpec_5= ruleEvaluationSpec | this_RegistrySpec_6= ruleRegistrySpec | this_ServingSpec_7= ruleServingSpec | this_MonitoringSpec_8= ruleMonitoringSpec | this_ExperimentTrackingSpec_9= ruleExperimentTrackingSpec | this_VersioningSpec_10= ruleVersioningSpec | this_ModelRegistrySpec_11= ruleModelRegistrySpec | this_DecisionPolicy_12= ruleDecisionPolicy | this_RetrainingPolicy_13= ruleRetrainingPolicy | this_GovernanceSpec_14= ruleGovernanceSpec | this_CiCdCtSpec_15= ruleCiCdCtSpec | this_FeatureStoreSpec_16= ruleFeatureStoreSpec | this_OperationalPlan_17= ruleOperationalPlan | this_InfraPlan_18= ruleInfraPlan | this_MlWorkflow_19= ruleMlWorkflow | this_EnvironmentSpec_20= ruleEnvironmentSpec | this_TerraformBackendSpec_21= ruleTerraformBackendSpec | this_RuntimeSpec_22= ruleRuntimeSpec | this_NodePoolSpec_23= ruleNodePoolSpec ) ;
    public final EObject ruleModel() throws RecognitionException {
        EObject current = null;

        EObject this_MetaModelSpec_0 = null;

        EObject this_ProjectSpec_1 = null;

        EObject this_DataAsset_2 = null;

        EObject this_FeatureSet_3 = null;

        EObject this_TrainingSpec_4 = null;

        EObject this_EvaluationSpec_5 = null;

        EObject this_RegistrySpec_6 = null;

        EObject this_ServingSpec_7 = null;

        EObject this_MonitoringSpec_8 = null;

        EObject this_ExperimentTrackingSpec_9 = null;

        EObject this_VersioningSpec_10 = null;

        EObject this_ModelRegistrySpec_11 = null;

        EObject this_DecisionPolicy_12 = null;

        EObject this_RetrainingPolicy_13 = null;

        EObject this_GovernanceSpec_14 = null;

        EObject this_CiCdCtSpec_15 = null;

        EObject this_FeatureStoreSpec_16 = null;

        EObject this_OperationalPlan_17 = null;

        EObject this_InfraPlan_18 = null;

        EObject this_MlWorkflow_19 = null;

        EObject this_EnvironmentSpec_20 = null;

        EObject this_TerraformBackendSpec_21 = null;

        EObject this_RuntimeSpec_22 = null;

        EObject this_NodePoolSpec_23 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:706:2: ( (this_MetaModelSpec_0= ruleMetaModelSpec | this_ProjectSpec_1= ruleProjectSpec | this_DataAsset_2= ruleDataAsset | this_FeatureSet_3= ruleFeatureSet | this_TrainingSpec_4= ruleTrainingSpec | this_EvaluationSpec_5= ruleEvaluationSpec | this_RegistrySpec_6= ruleRegistrySpec | this_ServingSpec_7= ruleServingSpec | this_MonitoringSpec_8= ruleMonitoringSpec | this_ExperimentTrackingSpec_9= ruleExperimentTrackingSpec | this_VersioningSpec_10= ruleVersioningSpec | this_ModelRegistrySpec_11= ruleModelRegistrySpec | this_DecisionPolicy_12= ruleDecisionPolicy | this_RetrainingPolicy_13= ruleRetrainingPolicy | this_GovernanceSpec_14= ruleGovernanceSpec | this_CiCdCtSpec_15= ruleCiCdCtSpec | this_FeatureStoreSpec_16= ruleFeatureStoreSpec | this_OperationalPlan_17= ruleOperationalPlan | this_InfraPlan_18= ruleInfraPlan | this_MlWorkflow_19= ruleMlWorkflow | this_EnvironmentSpec_20= ruleEnvironmentSpec | this_TerraformBackendSpec_21= ruleTerraformBackendSpec | this_RuntimeSpec_22= ruleRuntimeSpec | this_NodePoolSpec_23= ruleNodePoolSpec ) )
            // InternalMlopsDsl.g:707:2: (this_MetaModelSpec_0= ruleMetaModelSpec | this_ProjectSpec_1= ruleProjectSpec | this_DataAsset_2= ruleDataAsset | this_FeatureSet_3= ruleFeatureSet | this_TrainingSpec_4= ruleTrainingSpec | this_EvaluationSpec_5= ruleEvaluationSpec | this_RegistrySpec_6= ruleRegistrySpec | this_ServingSpec_7= ruleServingSpec | this_MonitoringSpec_8= ruleMonitoringSpec | this_ExperimentTrackingSpec_9= ruleExperimentTrackingSpec | this_VersioningSpec_10= ruleVersioningSpec | this_ModelRegistrySpec_11= ruleModelRegistrySpec | this_DecisionPolicy_12= ruleDecisionPolicy | this_RetrainingPolicy_13= ruleRetrainingPolicy | this_GovernanceSpec_14= ruleGovernanceSpec | this_CiCdCtSpec_15= ruleCiCdCtSpec | this_FeatureStoreSpec_16= ruleFeatureStoreSpec | this_OperationalPlan_17= ruleOperationalPlan | this_InfraPlan_18= ruleInfraPlan | this_MlWorkflow_19= ruleMlWorkflow | this_EnvironmentSpec_20= ruleEnvironmentSpec | this_TerraformBackendSpec_21= ruleTerraformBackendSpec | this_RuntimeSpec_22= ruleRuntimeSpec | this_NodePoolSpec_23= ruleNodePoolSpec )
            {
            // InternalMlopsDsl.g:707:2: (this_MetaModelSpec_0= ruleMetaModelSpec | this_ProjectSpec_1= ruleProjectSpec | this_DataAsset_2= ruleDataAsset | this_FeatureSet_3= ruleFeatureSet | this_TrainingSpec_4= ruleTrainingSpec | this_EvaluationSpec_5= ruleEvaluationSpec | this_RegistrySpec_6= ruleRegistrySpec | this_ServingSpec_7= ruleServingSpec | this_MonitoringSpec_8= ruleMonitoringSpec | this_ExperimentTrackingSpec_9= ruleExperimentTrackingSpec | this_VersioningSpec_10= ruleVersioningSpec | this_ModelRegistrySpec_11= ruleModelRegistrySpec | this_DecisionPolicy_12= ruleDecisionPolicy | this_RetrainingPolicy_13= ruleRetrainingPolicy | this_GovernanceSpec_14= ruleGovernanceSpec | this_CiCdCtSpec_15= ruleCiCdCtSpec | this_FeatureStoreSpec_16= ruleFeatureStoreSpec | this_OperationalPlan_17= ruleOperationalPlan | this_InfraPlan_18= ruleInfraPlan | this_MlWorkflow_19= ruleMlWorkflow | this_EnvironmentSpec_20= ruleEnvironmentSpec | this_TerraformBackendSpec_21= ruleTerraformBackendSpec | this_RuntimeSpec_22= ruleRuntimeSpec | this_NodePoolSpec_23= ruleNodePoolSpec )
            int alt10=24;
            switch ( input.LA(1) ) {
            case 38:
                {
                alt10=1;
                }
                break;
            case 47:
                {
                alt10=2;
                }
                break;
            case 62:
                {
                alt10=3;
                }
                break;
            case 80:
                {
                alt10=4;
                }
                break;
            case 81:
                {
                alt10=5;
                }
                break;
            case 92:
                {
                alt10=6;
                }
                break;
            case 95:
                {
                alt10=7;
                }
                break;
            case 100:
                {
                alt10=8;
                }
                break;
            case 111:
                {
                alt10=9;
                }
                break;
            case 116:
                {
                alt10=10;
                }
                break;
            case 124:
                {
                alt10=11;
                }
                break;
            case 130:
                {
                alt10=12;
                }
                break;
            case 141:
                {
                alt10=13;
                }
                break;
            case 150:
                {
                alt10=14;
                }
                break;
            case 157:
                {
                alt10=15;
                }
                break;
            case 166:
                {
                alt10=16;
                }
                break;
            case 175:
                {
                alt10=17;
                }
                break;
            case 179:
                {
                alt10=18;
                }
                break;
            case 214:
                {
                alt10=19;
                }
                break;
            case 242:
                {
                alt10=20;
                }
                break;
            case 12:
                {
                alt10=21;
                }
                break;
            case 17:
                {
                alt10=22;
                }
                break;
            case 33:
                {
                alt10=23;
                }
                break;
            case 24:
                {
                alt10=24;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // InternalMlopsDsl.g:708:3: this_MetaModelSpec_0= ruleMetaModelSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getMetaModelSpecParserRuleCall_0());
                    		
                    pushFollow(FOLLOW_2);
                    this_MetaModelSpec_0=ruleMetaModelSpec();

                    state._fsp--;


                    			current = this_MetaModelSpec_0;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:717:3: this_ProjectSpec_1= ruleProjectSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getProjectSpecParserRuleCall_1());
                    		
                    pushFollow(FOLLOW_2);
                    this_ProjectSpec_1=ruleProjectSpec();

                    state._fsp--;


                    			current = this_ProjectSpec_1;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:726:3: this_DataAsset_2= ruleDataAsset
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getDataAssetParserRuleCall_2());
                    		
                    pushFollow(FOLLOW_2);
                    this_DataAsset_2=ruleDataAsset();

                    state._fsp--;


                    			current = this_DataAsset_2;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:735:3: this_FeatureSet_3= ruleFeatureSet
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getFeatureSetParserRuleCall_3());
                    		
                    pushFollow(FOLLOW_2);
                    this_FeatureSet_3=ruleFeatureSet();

                    state._fsp--;


                    			current = this_FeatureSet_3;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:744:3: this_TrainingSpec_4= ruleTrainingSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getTrainingSpecParserRuleCall_4());
                    		
                    pushFollow(FOLLOW_2);
                    this_TrainingSpec_4=ruleTrainingSpec();

                    state._fsp--;


                    			current = this_TrainingSpec_4;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 6 :
                    // InternalMlopsDsl.g:753:3: this_EvaluationSpec_5= ruleEvaluationSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getEvaluationSpecParserRuleCall_5());
                    		
                    pushFollow(FOLLOW_2);
                    this_EvaluationSpec_5=ruleEvaluationSpec();

                    state._fsp--;


                    			current = this_EvaluationSpec_5;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 7 :
                    // InternalMlopsDsl.g:762:3: this_RegistrySpec_6= ruleRegistrySpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getRegistrySpecParserRuleCall_6());
                    		
                    pushFollow(FOLLOW_2);
                    this_RegistrySpec_6=ruleRegistrySpec();

                    state._fsp--;


                    			current = this_RegistrySpec_6;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 8 :
                    // InternalMlopsDsl.g:771:3: this_ServingSpec_7= ruleServingSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getServingSpecParserRuleCall_7());
                    		
                    pushFollow(FOLLOW_2);
                    this_ServingSpec_7=ruleServingSpec();

                    state._fsp--;


                    			current = this_ServingSpec_7;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 9 :
                    // InternalMlopsDsl.g:780:3: this_MonitoringSpec_8= ruleMonitoringSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getMonitoringSpecParserRuleCall_8());
                    		
                    pushFollow(FOLLOW_2);
                    this_MonitoringSpec_8=ruleMonitoringSpec();

                    state._fsp--;


                    			current = this_MonitoringSpec_8;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 10 :
                    // InternalMlopsDsl.g:789:3: this_ExperimentTrackingSpec_9= ruleExperimentTrackingSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getExperimentTrackingSpecParserRuleCall_9());
                    		
                    pushFollow(FOLLOW_2);
                    this_ExperimentTrackingSpec_9=ruleExperimentTrackingSpec();

                    state._fsp--;


                    			current = this_ExperimentTrackingSpec_9;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 11 :
                    // InternalMlopsDsl.g:798:3: this_VersioningSpec_10= ruleVersioningSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getVersioningSpecParserRuleCall_10());
                    		
                    pushFollow(FOLLOW_2);
                    this_VersioningSpec_10=ruleVersioningSpec();

                    state._fsp--;


                    			current = this_VersioningSpec_10;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 12 :
                    // InternalMlopsDsl.g:807:3: this_ModelRegistrySpec_11= ruleModelRegistrySpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getModelRegistrySpecParserRuleCall_11());
                    		
                    pushFollow(FOLLOW_2);
                    this_ModelRegistrySpec_11=ruleModelRegistrySpec();

                    state._fsp--;


                    			current = this_ModelRegistrySpec_11;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 13 :
                    // InternalMlopsDsl.g:816:3: this_DecisionPolicy_12= ruleDecisionPolicy
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getDecisionPolicyParserRuleCall_12());
                    		
                    pushFollow(FOLLOW_2);
                    this_DecisionPolicy_12=ruleDecisionPolicy();

                    state._fsp--;


                    			current = this_DecisionPolicy_12;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 14 :
                    // InternalMlopsDsl.g:825:3: this_RetrainingPolicy_13= ruleRetrainingPolicy
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getRetrainingPolicyParserRuleCall_13());
                    		
                    pushFollow(FOLLOW_2);
                    this_RetrainingPolicy_13=ruleRetrainingPolicy();

                    state._fsp--;


                    			current = this_RetrainingPolicy_13;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 15 :
                    // InternalMlopsDsl.g:834:3: this_GovernanceSpec_14= ruleGovernanceSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getGovernanceSpecParserRuleCall_14());
                    		
                    pushFollow(FOLLOW_2);
                    this_GovernanceSpec_14=ruleGovernanceSpec();

                    state._fsp--;


                    			current = this_GovernanceSpec_14;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 16 :
                    // InternalMlopsDsl.g:843:3: this_CiCdCtSpec_15= ruleCiCdCtSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getCiCdCtSpecParserRuleCall_15());
                    		
                    pushFollow(FOLLOW_2);
                    this_CiCdCtSpec_15=ruleCiCdCtSpec();

                    state._fsp--;


                    			current = this_CiCdCtSpec_15;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 17 :
                    // InternalMlopsDsl.g:852:3: this_FeatureStoreSpec_16= ruleFeatureStoreSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getFeatureStoreSpecParserRuleCall_16());
                    		
                    pushFollow(FOLLOW_2);
                    this_FeatureStoreSpec_16=ruleFeatureStoreSpec();

                    state._fsp--;


                    			current = this_FeatureStoreSpec_16;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 18 :
                    // InternalMlopsDsl.g:861:3: this_OperationalPlan_17= ruleOperationalPlan
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getOperationalPlanParserRuleCall_17());
                    		
                    pushFollow(FOLLOW_2);
                    this_OperationalPlan_17=ruleOperationalPlan();

                    state._fsp--;


                    			current = this_OperationalPlan_17;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 19 :
                    // InternalMlopsDsl.g:870:3: this_InfraPlan_18= ruleInfraPlan
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getInfraPlanParserRuleCall_18());
                    		
                    pushFollow(FOLLOW_2);
                    this_InfraPlan_18=ruleInfraPlan();

                    state._fsp--;


                    			current = this_InfraPlan_18;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 20 :
                    // InternalMlopsDsl.g:879:3: this_MlWorkflow_19= ruleMlWorkflow
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getMlWorkflowParserRuleCall_19());
                    		
                    pushFollow(FOLLOW_2);
                    this_MlWorkflow_19=ruleMlWorkflow();

                    state._fsp--;


                    			current = this_MlWorkflow_19;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 21 :
                    // InternalMlopsDsl.g:888:3: this_EnvironmentSpec_20= ruleEnvironmentSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getEnvironmentSpecParserRuleCall_20());
                    		
                    pushFollow(FOLLOW_2);
                    this_EnvironmentSpec_20=ruleEnvironmentSpec();

                    state._fsp--;


                    			current = this_EnvironmentSpec_20;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 22 :
                    // InternalMlopsDsl.g:897:3: this_TerraformBackendSpec_21= ruleTerraformBackendSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getTerraformBackendSpecParserRuleCall_21());
                    		
                    pushFollow(FOLLOW_2);
                    this_TerraformBackendSpec_21=ruleTerraformBackendSpec();

                    state._fsp--;


                    			current = this_TerraformBackendSpec_21;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 23 :
                    // InternalMlopsDsl.g:906:3: this_RuntimeSpec_22= ruleRuntimeSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getRuntimeSpecParserRuleCall_22());
                    		
                    pushFollow(FOLLOW_2);
                    this_RuntimeSpec_22=ruleRuntimeSpec();

                    state._fsp--;


                    			current = this_RuntimeSpec_22;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;
                case 24 :
                    // InternalMlopsDsl.g:915:3: this_NodePoolSpec_23= ruleNodePoolSpec
                    {

                    			newCompositeNode(grammarAccess.getModelAccess().getNodePoolSpecParserRuleCall_23());
                    		
                    pushFollow(FOLLOW_2);
                    this_NodePoolSpec_23=ruleNodePoolSpec();

                    state._fsp--;


                    			current = this_NodePoolSpec_23;
                    			afterParserOrEnumRuleCall();
                    		

                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModel"


    // $ANTLR start "entryRuleMetaModelSpec"
    // InternalMlopsDsl.g:927:1: entryRuleMetaModelSpec returns [EObject current=null] : iv_ruleMetaModelSpec= ruleMetaModelSpec EOF ;
    public final EObject entryRuleMetaModelSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMetaModelSpec = null;


        try {
            // InternalMlopsDsl.g:927:54: (iv_ruleMetaModelSpec= ruleMetaModelSpec EOF )
            // InternalMlopsDsl.g:928:2: iv_ruleMetaModelSpec= ruleMetaModelSpec EOF
            {
             newCompositeNode(grammarAccess.getMetaModelSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMetaModelSpec=ruleMetaModelSpec();

            state._fsp--;

             current =iv_ruleMetaModelSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMetaModelSpec"


    // $ANTLR start "ruleMetaModelSpec"
    // InternalMlopsDsl.g:934:1: ruleMetaModelSpec returns [EObject current=null] : (otherlv_0= 'MetaModelSpec' otherlv_1= '{' otherlv_2= 'version' otherlv_3= '=' ( (lv_version_4_0= RULE_STRING ) ) (otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) ) )? otherlv_8= 'createdBy' otherlv_9= '=' ( (lv_createdBy_10_0= RULE_STRING ) ) otherlv_11= 'createdAt' otherlv_12= '=' ( (lv_createdAt_13_0= RULE_STRING ) ) otherlv_14= 'project' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'plan' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'infra' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'workflow' otherlv_24= '=' ( (lv_workflow_25_0= ruleMlWorkflow ) ) otherlv_26= '}' ) ;
    public final EObject ruleMetaModelSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_version_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_schemaVersion_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_createdBy_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_createdAt_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        EObject lv_workflow_25_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:940:2: ( (otherlv_0= 'MetaModelSpec' otherlv_1= '{' otherlv_2= 'version' otherlv_3= '=' ( (lv_version_4_0= RULE_STRING ) ) (otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) ) )? otherlv_8= 'createdBy' otherlv_9= '=' ( (lv_createdBy_10_0= RULE_STRING ) ) otherlv_11= 'createdAt' otherlv_12= '=' ( (lv_createdAt_13_0= RULE_STRING ) ) otherlv_14= 'project' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'plan' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'infra' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'workflow' otherlv_24= '=' ( (lv_workflow_25_0= ruleMlWorkflow ) ) otherlv_26= '}' ) )
            // InternalMlopsDsl.g:941:2: (otherlv_0= 'MetaModelSpec' otherlv_1= '{' otherlv_2= 'version' otherlv_3= '=' ( (lv_version_4_0= RULE_STRING ) ) (otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) ) )? otherlv_8= 'createdBy' otherlv_9= '=' ( (lv_createdBy_10_0= RULE_STRING ) ) otherlv_11= 'createdAt' otherlv_12= '=' ( (lv_createdAt_13_0= RULE_STRING ) ) otherlv_14= 'project' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'plan' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'infra' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'workflow' otherlv_24= '=' ( (lv_workflow_25_0= ruleMlWorkflow ) ) otherlv_26= '}' )
            {
            // InternalMlopsDsl.g:941:2: (otherlv_0= 'MetaModelSpec' otherlv_1= '{' otherlv_2= 'version' otherlv_3= '=' ( (lv_version_4_0= RULE_STRING ) ) (otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) ) )? otherlv_8= 'createdBy' otherlv_9= '=' ( (lv_createdBy_10_0= RULE_STRING ) ) otherlv_11= 'createdAt' otherlv_12= '=' ( (lv_createdAt_13_0= RULE_STRING ) ) otherlv_14= 'project' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'plan' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'infra' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'workflow' otherlv_24= '=' ( (lv_workflow_25_0= ruleMlWorkflow ) ) otherlv_26= '}' )
            // InternalMlopsDsl.g:942:3: otherlv_0= 'MetaModelSpec' otherlv_1= '{' otherlv_2= 'version' otherlv_3= '=' ( (lv_version_4_0= RULE_STRING ) ) (otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) ) )? otherlv_8= 'createdBy' otherlv_9= '=' ( (lv_createdBy_10_0= RULE_STRING ) ) otherlv_11= 'createdAt' otherlv_12= '=' ( (lv_createdAt_13_0= RULE_STRING ) ) otherlv_14= 'project' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'plan' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'infra' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'workflow' otherlv_24= '=' ( (lv_workflow_25_0= ruleMlWorkflow ) ) otherlv_26= '}'
            {
            otherlv_0=(Token)match(input,38,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMetaModelSpecAccess().getMetaModelSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_27); 

            			newLeafNode(otherlv_1, grammarAccess.getMetaModelSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,39,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getMetaModelSpecAccess().getVersionKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:958:3: ( (lv_version_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:959:4: (lv_version_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:959:4: (lv_version_4_0= RULE_STRING )
            // InternalMlopsDsl.g:960:5: lv_version_4_0= RULE_STRING
            {
            lv_version_4_0=(Token)match(input,RULE_STRING,FOLLOW_28); 

            					newLeafNode(lv_version_4_0, grammarAccess.getMetaModelSpecAccess().getVersionSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMetaModelSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"version",
            						lv_version_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:976:3: (otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) ) )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==40) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // InternalMlopsDsl.g:977:4: otherlv_5= 'schemaVersion' otherlv_6= '=' ( (lv_schemaVersion_7_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,40,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getMetaModelSpecAccess().getSchemaVersionKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_6, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:985:4: ( (lv_schemaVersion_7_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:986:5: (lv_schemaVersion_7_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:986:5: (lv_schemaVersion_7_0= RULE_STRING )
                    // InternalMlopsDsl.g:987:6: lv_schemaVersion_7_0= RULE_STRING
                    {
                    lv_schemaVersion_7_0=(Token)match(input,RULE_STRING,FOLLOW_29); 

                    						newLeafNode(lv_schemaVersion_7_0, grammarAccess.getMetaModelSpecAccess().getSchemaVersionSTRINGTerminalRuleCall_5_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMetaModelSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"schemaVersion",
                    							lv_schemaVersion_7_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_8=(Token)match(input,41,FOLLOW_6); 

            			newLeafNode(otherlv_8, grammarAccess.getMetaModelSpecAccess().getCreatedByKeyword_6());
            		
            otherlv_9=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_9, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:1012:3: ( (lv_createdBy_10_0= RULE_STRING ) )
            // InternalMlopsDsl.g:1013:4: (lv_createdBy_10_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:1013:4: (lv_createdBy_10_0= RULE_STRING )
            // InternalMlopsDsl.g:1014:5: lv_createdBy_10_0= RULE_STRING
            {
            lv_createdBy_10_0=(Token)match(input,RULE_STRING,FOLLOW_30); 

            					newLeafNode(lv_createdBy_10_0, grammarAccess.getMetaModelSpecAccess().getCreatedBySTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMetaModelSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"createdBy",
            						lv_createdBy_10_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_11=(Token)match(input,42,FOLLOW_6); 

            			newLeafNode(otherlv_11, grammarAccess.getMetaModelSpecAccess().getCreatedAtKeyword_9());
            		
            otherlv_12=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_12, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_10());
            		
            // InternalMlopsDsl.g:1038:3: ( (lv_createdAt_13_0= RULE_STRING ) )
            // InternalMlopsDsl.g:1039:4: (lv_createdAt_13_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:1039:4: (lv_createdAt_13_0= RULE_STRING )
            // InternalMlopsDsl.g:1040:5: lv_createdAt_13_0= RULE_STRING
            {
            lv_createdAt_13_0=(Token)match(input,RULE_STRING,FOLLOW_31); 

            					newLeafNode(lv_createdAt_13_0, grammarAccess.getMetaModelSpecAccess().getCreatedAtSTRINGTerminalRuleCall_11_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMetaModelSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"createdAt",
            						lv_createdAt_13_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_14=(Token)match(input,43,FOLLOW_6); 

            			newLeafNode(otherlv_14, grammarAccess.getMetaModelSpecAccess().getProjectKeyword_12());
            		
            otherlv_15=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_15, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_13());
            		
            // InternalMlopsDsl.g:1064:3: ( (otherlv_16= RULE_ID ) )
            // InternalMlopsDsl.g:1065:4: (otherlv_16= RULE_ID )
            {
            // InternalMlopsDsl.g:1065:4: (otherlv_16= RULE_ID )
            // InternalMlopsDsl.g:1066:5: otherlv_16= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMetaModelSpecRule());
            					}
            				
            otherlv_16=(Token)match(input,RULE_ID,FOLLOW_33); 

            					newLeafNode(otherlv_16, grammarAccess.getMetaModelSpecAccess().getProjectProjectSpecCrossReference_14_0());
            				

            }


            }

            otherlv_17=(Token)match(input,44,FOLLOW_6); 

            			newLeafNode(otherlv_17, grammarAccess.getMetaModelSpecAccess().getPlanKeyword_15());
            		
            otherlv_18=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_18, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_16());
            		
            // InternalMlopsDsl.g:1085:3: ( (otherlv_19= RULE_ID ) )
            // InternalMlopsDsl.g:1086:4: (otherlv_19= RULE_ID )
            {
            // InternalMlopsDsl.g:1086:4: (otherlv_19= RULE_ID )
            // InternalMlopsDsl.g:1087:5: otherlv_19= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMetaModelSpecRule());
            					}
            				
            otherlv_19=(Token)match(input,RULE_ID,FOLLOW_34); 

            					newLeafNode(otherlv_19, grammarAccess.getMetaModelSpecAccess().getPlanOperationalPlanCrossReference_17_0());
            				

            }


            }

            otherlv_20=(Token)match(input,45,FOLLOW_6); 

            			newLeafNode(otherlv_20, grammarAccess.getMetaModelSpecAccess().getInfraKeyword_18());
            		
            otherlv_21=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_21, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_19());
            		
            // InternalMlopsDsl.g:1106:3: ( (otherlv_22= RULE_ID ) )
            // InternalMlopsDsl.g:1107:4: (otherlv_22= RULE_ID )
            {
            // InternalMlopsDsl.g:1107:4: (otherlv_22= RULE_ID )
            // InternalMlopsDsl.g:1108:5: otherlv_22= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMetaModelSpecRule());
            					}
            				
            otherlv_22=(Token)match(input,RULE_ID,FOLLOW_35); 

            					newLeafNode(otherlv_22, grammarAccess.getMetaModelSpecAccess().getInfraInfraPlanCrossReference_20_0());
            				

            }


            }

            otherlv_23=(Token)match(input,46,FOLLOW_6); 

            			newLeafNode(otherlv_23, grammarAccess.getMetaModelSpecAccess().getWorkflowKeyword_21());
            		
            otherlv_24=(Token)match(input,15,FOLLOW_36); 

            			newLeafNode(otherlv_24, grammarAccess.getMetaModelSpecAccess().getEqualsSignKeyword_22());
            		
            // InternalMlopsDsl.g:1127:3: ( (lv_workflow_25_0= ruleMlWorkflow ) )
            // InternalMlopsDsl.g:1128:4: (lv_workflow_25_0= ruleMlWorkflow )
            {
            // InternalMlopsDsl.g:1128:4: (lv_workflow_25_0= ruleMlWorkflow )
            // InternalMlopsDsl.g:1129:5: lv_workflow_25_0= ruleMlWorkflow
            {

            					newCompositeNode(grammarAccess.getMetaModelSpecAccess().getWorkflowMlWorkflowParserRuleCall_23_0());
            				
            pushFollow(FOLLOW_8);
            lv_workflow_25_0=ruleMlWorkflow();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMetaModelSpecRule());
            					}
            					set(
            						current,
            						"workflow",
            						lv_workflow_25_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.MlWorkflow");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_26=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_26, grammarAccess.getMetaModelSpecAccess().getRightCurlyBracketKeyword_24());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMetaModelSpec"


    // $ANTLR start "entryRuleProjectSpec"
    // InternalMlopsDsl.g:1154:1: entryRuleProjectSpec returns [EObject current=null] : iv_ruleProjectSpec= ruleProjectSpec EOF ;
    public final EObject entryRuleProjectSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleProjectSpec = null;


        try {
            // InternalMlopsDsl.g:1154:52: (iv_ruleProjectSpec= ruleProjectSpec EOF )
            // InternalMlopsDsl.g:1155:2: iv_ruleProjectSpec= ruleProjectSpec EOF
            {
             newCompositeNode(grammarAccess.getProjectSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleProjectSpec=ruleProjectSpec();

            state._fsp--;

             current =iv_ruleProjectSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleProjectSpec"


    // $ANTLR start "ruleProjectSpec"
    // InternalMlopsDsl.g:1161:1: ruleProjectSpec returns [EObject current=null] : (otherlv_0= 'ProjectSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'data' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= 'features' otherlv_9= '=' ( (otherlv_10= RULE_ID ) ) otherlv_11= 'train' otherlv_12= '=' ( (otherlv_13= RULE_ID ) ) otherlv_14= 'eval' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'registry' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'serving' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'monitoring' otherlv_24= '=' ( (otherlv_25= RULE_ID ) ) otherlv_26= 'tracking' otherlv_27= '=' ( (otherlv_28= RULE_ID ) ) otherlv_29= 'versioning' otherlv_30= '=' ( (otherlv_31= RULE_ID ) ) otherlv_32= 'modelRegistry' otherlv_33= '=' ( (otherlv_34= RULE_ID ) ) otherlv_35= 'decisionPolicy' otherlv_36= '=' ( (otherlv_37= RULE_ID ) ) otherlv_38= 'retrainingPolicy' otherlv_39= '=' ( (otherlv_40= RULE_ID ) ) otherlv_41= 'governance' otherlv_42= '=' ( (otherlv_43= RULE_ID ) ) otherlv_44= 'cicdct' otherlv_45= '=' ( (otherlv_46= RULE_ID ) ) otherlv_47= '}' ) ;
    public final EObject ruleProjectSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_29=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_32=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token otherlv_35=null;
        Token otherlv_36=null;
        Token otherlv_37=null;
        Token otherlv_38=null;
        Token otherlv_39=null;
        Token otherlv_40=null;
        Token otherlv_41=null;
        Token otherlv_42=null;
        Token otherlv_43=null;
        Token otherlv_44=null;
        Token otherlv_45=null;
        Token otherlv_46=null;
        Token otherlv_47=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:1167:2: ( (otherlv_0= 'ProjectSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'data' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= 'features' otherlv_9= '=' ( (otherlv_10= RULE_ID ) ) otherlv_11= 'train' otherlv_12= '=' ( (otherlv_13= RULE_ID ) ) otherlv_14= 'eval' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'registry' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'serving' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'monitoring' otherlv_24= '=' ( (otherlv_25= RULE_ID ) ) otherlv_26= 'tracking' otherlv_27= '=' ( (otherlv_28= RULE_ID ) ) otherlv_29= 'versioning' otherlv_30= '=' ( (otherlv_31= RULE_ID ) ) otherlv_32= 'modelRegistry' otherlv_33= '=' ( (otherlv_34= RULE_ID ) ) otherlv_35= 'decisionPolicy' otherlv_36= '=' ( (otherlv_37= RULE_ID ) ) otherlv_38= 'retrainingPolicy' otherlv_39= '=' ( (otherlv_40= RULE_ID ) ) otherlv_41= 'governance' otherlv_42= '=' ( (otherlv_43= RULE_ID ) ) otherlv_44= 'cicdct' otherlv_45= '=' ( (otherlv_46= RULE_ID ) ) otherlv_47= '}' ) )
            // InternalMlopsDsl.g:1168:2: (otherlv_0= 'ProjectSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'data' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= 'features' otherlv_9= '=' ( (otherlv_10= RULE_ID ) ) otherlv_11= 'train' otherlv_12= '=' ( (otherlv_13= RULE_ID ) ) otherlv_14= 'eval' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'registry' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'serving' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'monitoring' otherlv_24= '=' ( (otherlv_25= RULE_ID ) ) otherlv_26= 'tracking' otherlv_27= '=' ( (otherlv_28= RULE_ID ) ) otherlv_29= 'versioning' otherlv_30= '=' ( (otherlv_31= RULE_ID ) ) otherlv_32= 'modelRegistry' otherlv_33= '=' ( (otherlv_34= RULE_ID ) ) otherlv_35= 'decisionPolicy' otherlv_36= '=' ( (otherlv_37= RULE_ID ) ) otherlv_38= 'retrainingPolicy' otherlv_39= '=' ( (otherlv_40= RULE_ID ) ) otherlv_41= 'governance' otherlv_42= '=' ( (otherlv_43= RULE_ID ) ) otherlv_44= 'cicdct' otherlv_45= '=' ( (otherlv_46= RULE_ID ) ) otherlv_47= '}' )
            {
            // InternalMlopsDsl.g:1168:2: (otherlv_0= 'ProjectSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'data' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= 'features' otherlv_9= '=' ( (otherlv_10= RULE_ID ) ) otherlv_11= 'train' otherlv_12= '=' ( (otherlv_13= RULE_ID ) ) otherlv_14= 'eval' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'registry' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'serving' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'monitoring' otherlv_24= '=' ( (otherlv_25= RULE_ID ) ) otherlv_26= 'tracking' otherlv_27= '=' ( (otherlv_28= RULE_ID ) ) otherlv_29= 'versioning' otherlv_30= '=' ( (otherlv_31= RULE_ID ) ) otherlv_32= 'modelRegistry' otherlv_33= '=' ( (otherlv_34= RULE_ID ) ) otherlv_35= 'decisionPolicy' otherlv_36= '=' ( (otherlv_37= RULE_ID ) ) otherlv_38= 'retrainingPolicy' otherlv_39= '=' ( (otherlv_40= RULE_ID ) ) otherlv_41= 'governance' otherlv_42= '=' ( (otherlv_43= RULE_ID ) ) otherlv_44= 'cicdct' otherlv_45= '=' ( (otherlv_46= RULE_ID ) ) otherlv_47= '}' )
            // InternalMlopsDsl.g:1169:3: otherlv_0= 'ProjectSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'data' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= 'features' otherlv_9= '=' ( (otherlv_10= RULE_ID ) ) otherlv_11= 'train' otherlv_12= '=' ( (otherlv_13= RULE_ID ) ) otherlv_14= 'eval' otherlv_15= '=' ( (otherlv_16= RULE_ID ) ) otherlv_17= 'registry' otherlv_18= '=' ( (otherlv_19= RULE_ID ) ) otherlv_20= 'serving' otherlv_21= '=' ( (otherlv_22= RULE_ID ) ) otherlv_23= 'monitoring' otherlv_24= '=' ( (otherlv_25= RULE_ID ) ) otherlv_26= 'tracking' otherlv_27= '=' ( (otherlv_28= RULE_ID ) ) otherlv_29= 'versioning' otherlv_30= '=' ( (otherlv_31= RULE_ID ) ) otherlv_32= 'modelRegistry' otherlv_33= '=' ( (otherlv_34= RULE_ID ) ) otherlv_35= 'decisionPolicy' otherlv_36= '=' ( (otherlv_37= RULE_ID ) ) otherlv_38= 'retrainingPolicy' otherlv_39= '=' ( (otherlv_40= RULE_ID ) ) otherlv_41= 'governance' otherlv_42= '=' ( (otherlv_43= RULE_ID ) ) otherlv_44= 'cicdct' otherlv_45= '=' ( (otherlv_46= RULE_ID ) ) otherlv_47= '}'
            {
            otherlv_0=(Token)match(input,47,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getProjectSpecAccess().getProjectSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getProjectSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getProjectSpecAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:1185:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:1186:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:1186:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:1187:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_37); 

            					newLeafNode(lv_name_4_0, grammarAccess.getProjectSpecAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,48,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getProjectSpecAccess().getDataKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_6, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:1211:3: ( (otherlv_7= RULE_ID ) )
            // InternalMlopsDsl.g:1212:4: (otherlv_7= RULE_ID )
            {
            // InternalMlopsDsl.g:1212:4: (otherlv_7= RULE_ID )
            // InternalMlopsDsl.g:1213:5: otherlv_7= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_7=(Token)match(input,RULE_ID,FOLLOW_38); 

            					newLeafNode(otherlv_7, grammarAccess.getProjectSpecAccess().getDataDataAssetCrossReference_7_0());
            				

            }


            }

            otherlv_8=(Token)match(input,49,FOLLOW_6); 

            			newLeafNode(otherlv_8, grammarAccess.getProjectSpecAccess().getFeaturesKeyword_8());
            		
            otherlv_9=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_9, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_9());
            		
            // InternalMlopsDsl.g:1232:3: ( (otherlv_10= RULE_ID ) )
            // InternalMlopsDsl.g:1233:4: (otherlv_10= RULE_ID )
            {
            // InternalMlopsDsl.g:1233:4: (otherlv_10= RULE_ID )
            // InternalMlopsDsl.g:1234:5: otherlv_10= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_10=(Token)match(input,RULE_ID,FOLLOW_39); 

            					newLeafNode(otherlv_10, grammarAccess.getProjectSpecAccess().getFeaturesFeatureSetCrossReference_10_0());
            				

            }


            }

            otherlv_11=(Token)match(input,50,FOLLOW_6); 

            			newLeafNode(otherlv_11, grammarAccess.getProjectSpecAccess().getTrainKeyword_11());
            		
            otherlv_12=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_12, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_12());
            		
            // InternalMlopsDsl.g:1253:3: ( (otherlv_13= RULE_ID ) )
            // InternalMlopsDsl.g:1254:4: (otherlv_13= RULE_ID )
            {
            // InternalMlopsDsl.g:1254:4: (otherlv_13= RULE_ID )
            // InternalMlopsDsl.g:1255:5: otherlv_13= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_13=(Token)match(input,RULE_ID,FOLLOW_40); 

            					newLeafNode(otherlv_13, grammarAccess.getProjectSpecAccess().getTrainTrainingSpecCrossReference_13_0());
            				

            }


            }

            otherlv_14=(Token)match(input,51,FOLLOW_6); 

            			newLeafNode(otherlv_14, grammarAccess.getProjectSpecAccess().getEvalKeyword_14());
            		
            otherlv_15=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_15, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_15());
            		
            // InternalMlopsDsl.g:1274:3: ( (otherlv_16= RULE_ID ) )
            // InternalMlopsDsl.g:1275:4: (otherlv_16= RULE_ID )
            {
            // InternalMlopsDsl.g:1275:4: (otherlv_16= RULE_ID )
            // InternalMlopsDsl.g:1276:5: otherlv_16= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_16=(Token)match(input,RULE_ID,FOLLOW_41); 

            					newLeafNode(otherlv_16, grammarAccess.getProjectSpecAccess().getEvalEvaluationSpecCrossReference_16_0());
            				

            }


            }

            otherlv_17=(Token)match(input,52,FOLLOW_6); 

            			newLeafNode(otherlv_17, grammarAccess.getProjectSpecAccess().getRegistryKeyword_17());
            		
            otherlv_18=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_18, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_18());
            		
            // InternalMlopsDsl.g:1295:3: ( (otherlv_19= RULE_ID ) )
            // InternalMlopsDsl.g:1296:4: (otherlv_19= RULE_ID )
            {
            // InternalMlopsDsl.g:1296:4: (otherlv_19= RULE_ID )
            // InternalMlopsDsl.g:1297:5: otherlv_19= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_19=(Token)match(input,RULE_ID,FOLLOW_42); 

            					newLeafNode(otherlv_19, grammarAccess.getProjectSpecAccess().getRegistryRegistrySpecCrossReference_19_0());
            				

            }


            }

            otherlv_20=(Token)match(input,53,FOLLOW_6); 

            			newLeafNode(otherlv_20, grammarAccess.getProjectSpecAccess().getServingKeyword_20());
            		
            otherlv_21=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_21, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_21());
            		
            // InternalMlopsDsl.g:1316:3: ( (otherlv_22= RULE_ID ) )
            // InternalMlopsDsl.g:1317:4: (otherlv_22= RULE_ID )
            {
            // InternalMlopsDsl.g:1317:4: (otherlv_22= RULE_ID )
            // InternalMlopsDsl.g:1318:5: otherlv_22= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_22=(Token)match(input,RULE_ID,FOLLOW_43); 

            					newLeafNode(otherlv_22, grammarAccess.getProjectSpecAccess().getServingServingSpecCrossReference_22_0());
            				

            }


            }

            otherlv_23=(Token)match(input,54,FOLLOW_6); 

            			newLeafNode(otherlv_23, grammarAccess.getProjectSpecAccess().getMonitoringKeyword_23());
            		
            otherlv_24=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_24, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_24());
            		
            // InternalMlopsDsl.g:1337:3: ( (otherlv_25= RULE_ID ) )
            // InternalMlopsDsl.g:1338:4: (otherlv_25= RULE_ID )
            {
            // InternalMlopsDsl.g:1338:4: (otherlv_25= RULE_ID )
            // InternalMlopsDsl.g:1339:5: otherlv_25= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_25=(Token)match(input,RULE_ID,FOLLOW_44); 

            					newLeafNode(otherlv_25, grammarAccess.getProjectSpecAccess().getMonitoringMonitoringSpecCrossReference_25_0());
            				

            }


            }

            otherlv_26=(Token)match(input,55,FOLLOW_6); 

            			newLeafNode(otherlv_26, grammarAccess.getProjectSpecAccess().getTrackingKeyword_26());
            		
            otherlv_27=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_27, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_27());
            		
            // InternalMlopsDsl.g:1358:3: ( (otherlv_28= RULE_ID ) )
            // InternalMlopsDsl.g:1359:4: (otherlv_28= RULE_ID )
            {
            // InternalMlopsDsl.g:1359:4: (otherlv_28= RULE_ID )
            // InternalMlopsDsl.g:1360:5: otherlv_28= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_28=(Token)match(input,RULE_ID,FOLLOW_45); 

            					newLeafNode(otherlv_28, grammarAccess.getProjectSpecAccess().getTrackingExperimentTrackingSpecCrossReference_28_0());
            				

            }


            }

            otherlv_29=(Token)match(input,56,FOLLOW_6); 

            			newLeafNode(otherlv_29, grammarAccess.getProjectSpecAccess().getVersioningKeyword_29());
            		
            otherlv_30=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_30, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_30());
            		
            // InternalMlopsDsl.g:1379:3: ( (otherlv_31= RULE_ID ) )
            // InternalMlopsDsl.g:1380:4: (otherlv_31= RULE_ID )
            {
            // InternalMlopsDsl.g:1380:4: (otherlv_31= RULE_ID )
            // InternalMlopsDsl.g:1381:5: otherlv_31= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_31=(Token)match(input,RULE_ID,FOLLOW_46); 

            					newLeafNode(otherlv_31, grammarAccess.getProjectSpecAccess().getVersioningVersioningSpecCrossReference_31_0());
            				

            }


            }

            otherlv_32=(Token)match(input,57,FOLLOW_6); 

            			newLeafNode(otherlv_32, grammarAccess.getProjectSpecAccess().getModelRegistryKeyword_32());
            		
            otherlv_33=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_33, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_33());
            		
            // InternalMlopsDsl.g:1400:3: ( (otherlv_34= RULE_ID ) )
            // InternalMlopsDsl.g:1401:4: (otherlv_34= RULE_ID )
            {
            // InternalMlopsDsl.g:1401:4: (otherlv_34= RULE_ID )
            // InternalMlopsDsl.g:1402:5: otherlv_34= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_34=(Token)match(input,RULE_ID,FOLLOW_47); 

            					newLeafNode(otherlv_34, grammarAccess.getProjectSpecAccess().getModelRegistryModelRegistrySpecCrossReference_34_0());
            				

            }


            }

            otherlv_35=(Token)match(input,58,FOLLOW_6); 

            			newLeafNode(otherlv_35, grammarAccess.getProjectSpecAccess().getDecisionPolicyKeyword_35());
            		
            otherlv_36=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_36, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_36());
            		
            // InternalMlopsDsl.g:1421:3: ( (otherlv_37= RULE_ID ) )
            // InternalMlopsDsl.g:1422:4: (otherlv_37= RULE_ID )
            {
            // InternalMlopsDsl.g:1422:4: (otherlv_37= RULE_ID )
            // InternalMlopsDsl.g:1423:5: otherlv_37= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_37=(Token)match(input,RULE_ID,FOLLOW_48); 

            					newLeafNode(otherlv_37, grammarAccess.getProjectSpecAccess().getDecisionPolicyDecisionPolicyCrossReference_37_0());
            				

            }


            }

            otherlv_38=(Token)match(input,59,FOLLOW_6); 

            			newLeafNode(otherlv_38, grammarAccess.getProjectSpecAccess().getRetrainingPolicyKeyword_38());
            		
            otherlv_39=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_39, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_39());
            		
            // InternalMlopsDsl.g:1442:3: ( (otherlv_40= RULE_ID ) )
            // InternalMlopsDsl.g:1443:4: (otherlv_40= RULE_ID )
            {
            // InternalMlopsDsl.g:1443:4: (otherlv_40= RULE_ID )
            // InternalMlopsDsl.g:1444:5: otherlv_40= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_40=(Token)match(input,RULE_ID,FOLLOW_49); 

            					newLeafNode(otherlv_40, grammarAccess.getProjectSpecAccess().getRetrainingPolicyRetrainingPolicyCrossReference_40_0());
            				

            }


            }

            otherlv_41=(Token)match(input,60,FOLLOW_6); 

            			newLeafNode(otherlv_41, grammarAccess.getProjectSpecAccess().getGovernanceKeyword_41());
            		
            otherlv_42=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_42, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_42());
            		
            // InternalMlopsDsl.g:1463:3: ( (otherlv_43= RULE_ID ) )
            // InternalMlopsDsl.g:1464:4: (otherlv_43= RULE_ID )
            {
            // InternalMlopsDsl.g:1464:4: (otherlv_43= RULE_ID )
            // InternalMlopsDsl.g:1465:5: otherlv_43= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_43=(Token)match(input,RULE_ID,FOLLOW_50); 

            					newLeafNode(otherlv_43, grammarAccess.getProjectSpecAccess().getGovernanceGovernanceSpecCrossReference_43_0());
            				

            }


            }

            otherlv_44=(Token)match(input,61,FOLLOW_6); 

            			newLeafNode(otherlv_44, grammarAccess.getProjectSpecAccess().getCicdctKeyword_44());
            		
            otherlv_45=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_45, grammarAccess.getProjectSpecAccess().getEqualsSignKeyword_45());
            		
            // InternalMlopsDsl.g:1484:3: ( (otherlv_46= RULE_ID ) )
            // InternalMlopsDsl.g:1485:4: (otherlv_46= RULE_ID )
            {
            // InternalMlopsDsl.g:1485:4: (otherlv_46= RULE_ID )
            // InternalMlopsDsl.g:1486:5: otherlv_46= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getProjectSpecRule());
            					}
            				
            otherlv_46=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(otherlv_46, grammarAccess.getProjectSpecAccess().getCicdctCiCdCtSpecCrossReference_46_0());
            				

            }


            }

            otherlv_47=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_47, grammarAccess.getProjectSpecAccess().getRightCurlyBracketKeyword_47());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleProjectSpec"


    // $ANTLR start "entryRuleDataAsset"
    // InternalMlopsDsl.g:1505:1: entryRuleDataAsset returns [EObject current=null] : iv_ruleDataAsset= ruleDataAsset EOF ;
    public final EObject entryRuleDataAsset() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDataAsset = null;


        try {
            // InternalMlopsDsl.g:1505:50: (iv_ruleDataAsset= ruleDataAsset EOF )
            // InternalMlopsDsl.g:1506:2: iv_ruleDataAsset= ruleDataAsset EOF
            {
             newCompositeNode(grammarAccess.getDataAssetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDataAsset=ruleDataAsset();

            state._fsp--;

             current =iv_ruleDataAsset; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDataAsset"


    // $ANTLR start "ruleDataAsset"
    // InternalMlopsDsl.g:1512:1: ruleDataAsset returns [EObject current=null] : (otherlv_0= 'DataAsset' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'source' otherlv_4= '=' ( (lv_source_5_0= ruleDataSource ) ) otherlv_6= 'modality' otherlv_7= '=' ( (lv_modality_8_0= ruleDataModality ) ) otherlv_9= 'location' otherlv_10= '=' ( (lv_location_11_0= RULE_STRING ) ) (otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) ) )? (otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) ) )? (otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) ) )? (otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) ) )? (otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) ) )? (otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) ) )? (otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) ) )? (otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) ) )? (otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']' )? (otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']' )? otherlv_50= '}' ) ;
    public final EObject ruleDataAsset() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_location_11_0=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token lv_format_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_schema_17_0=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_version_20_0=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token lv_license_29_0=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token lv_privacy_32_0=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token lv_hash_35_0=null;
        Token otherlv_36=null;
        Token otherlv_37=null;
        Token otherlv_38=null;
        Token otherlv_40=null;
        Token otherlv_42=null;
        Token otherlv_43=null;
        Token otherlv_44=null;
        Token otherlv_45=null;
        Token otherlv_47=null;
        Token otherlv_49=null;
        Token otherlv_50=null;
        Enumerator lv_source_5_0 = null;

        Enumerator lv_modality_8_0 = null;

        AntlrDatatypeRuleToken lv_sizeBytes_23_0 = null;

        AntlrDatatypeRuleToken lv_recordCount_26_0 = null;

        EObject lv_splits_39_0 = null;

        EObject lv_splits_41_0 = null;

        EObject lv_annotations_46_0 = null;

        EObject lv_annotations_48_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:1518:2: ( (otherlv_0= 'DataAsset' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'source' otherlv_4= '=' ( (lv_source_5_0= ruleDataSource ) ) otherlv_6= 'modality' otherlv_7= '=' ( (lv_modality_8_0= ruleDataModality ) ) otherlv_9= 'location' otherlv_10= '=' ( (lv_location_11_0= RULE_STRING ) ) (otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) ) )? (otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) ) )? (otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) ) )? (otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) ) )? (otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) ) )? (otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) ) )? (otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) ) )? (otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) ) )? (otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']' )? (otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']' )? otherlv_50= '}' ) )
            // InternalMlopsDsl.g:1519:2: (otherlv_0= 'DataAsset' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'source' otherlv_4= '=' ( (lv_source_5_0= ruleDataSource ) ) otherlv_6= 'modality' otherlv_7= '=' ( (lv_modality_8_0= ruleDataModality ) ) otherlv_9= 'location' otherlv_10= '=' ( (lv_location_11_0= RULE_STRING ) ) (otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) ) )? (otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) ) )? (otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) ) )? (otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) ) )? (otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) ) )? (otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) ) )? (otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) ) )? (otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) ) )? (otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']' )? (otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']' )? otherlv_50= '}' )
            {
            // InternalMlopsDsl.g:1519:2: (otherlv_0= 'DataAsset' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'source' otherlv_4= '=' ( (lv_source_5_0= ruleDataSource ) ) otherlv_6= 'modality' otherlv_7= '=' ( (lv_modality_8_0= ruleDataModality ) ) otherlv_9= 'location' otherlv_10= '=' ( (lv_location_11_0= RULE_STRING ) ) (otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) ) )? (otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) ) )? (otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) ) )? (otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) ) )? (otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) ) )? (otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) ) )? (otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) ) )? (otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) ) )? (otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']' )? (otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']' )? otherlv_50= '}' )
            // InternalMlopsDsl.g:1520:3: otherlv_0= 'DataAsset' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'source' otherlv_4= '=' ( (lv_source_5_0= ruleDataSource ) ) otherlv_6= 'modality' otherlv_7= '=' ( (lv_modality_8_0= ruleDataModality ) ) otherlv_9= 'location' otherlv_10= '=' ( (lv_location_11_0= RULE_STRING ) ) (otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) ) )? (otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) ) )? (otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) ) )? (otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) ) )? (otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) ) )? (otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) ) )? (otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) ) )? (otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) ) )? (otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']' )? (otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']' )? otherlv_50= '}'
            {
            otherlv_0=(Token)match(input,62,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getDataAssetAccess().getDataAssetKeyword_0());
            		
            // InternalMlopsDsl.g:1524:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:1525:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:1525:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:1526:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getDataAssetAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDataAssetRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_51); 

            			newLeafNode(otherlv_2, grammarAccess.getDataAssetAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,63,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getDataAssetAccess().getSourceKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_52); 

            			newLeafNode(otherlv_4, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:1554:3: ( (lv_source_5_0= ruleDataSource ) )
            // InternalMlopsDsl.g:1555:4: (lv_source_5_0= ruleDataSource )
            {
            // InternalMlopsDsl.g:1555:4: (lv_source_5_0= ruleDataSource )
            // InternalMlopsDsl.g:1556:5: lv_source_5_0= ruleDataSource
            {

            					newCompositeNode(grammarAccess.getDataAssetAccess().getSourceDataSourceEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_53);
            lv_source_5_0=ruleDataSource();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDataAssetRule());
            					}
            					set(
            						current,
            						"source",
            						lv_source_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.DataSource");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,64,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getDataAssetAccess().getModalityKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_54); 

            			newLeafNode(otherlv_7, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:1581:3: ( (lv_modality_8_0= ruleDataModality ) )
            // InternalMlopsDsl.g:1582:4: (lv_modality_8_0= ruleDataModality )
            {
            // InternalMlopsDsl.g:1582:4: (lv_modality_8_0= ruleDataModality )
            // InternalMlopsDsl.g:1583:5: lv_modality_8_0= ruleDataModality
            {

            					newCompositeNode(grammarAccess.getDataAssetAccess().getModalityDataModalityEnumRuleCall_8_0());
            				
            pushFollow(FOLLOW_55);
            lv_modality_8_0=ruleDataModality();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDataAssetRule());
            					}
            					set(
            						current,
            						"modality",
            						lv_modality_8_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.DataModality");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_9=(Token)match(input,65,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getDataAssetAccess().getLocationKeyword_9());
            		
            otherlv_10=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_10, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_10());
            		
            // InternalMlopsDsl.g:1608:3: ( (lv_location_11_0= RULE_STRING ) )
            // InternalMlopsDsl.g:1609:4: (lv_location_11_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:1609:4: (lv_location_11_0= RULE_STRING )
            // InternalMlopsDsl.g:1610:5: lv_location_11_0= RULE_STRING
            {
            lv_location_11_0=(Token)match(input,RULE_STRING,FOLLOW_56); 

            					newLeafNode(lv_location_11_0, grammarAccess.getDataAssetAccess().getLocationSTRINGTerminalRuleCall_11_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDataAssetRule());
            					}
            					setWithLastConsumed(
            						current,
            						"location",
            						lv_location_11_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:1626:3: (otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) ) )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==66) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // InternalMlopsDsl.g:1627:4: otherlv_12= 'format' otherlv_13= '=' ( (lv_format_14_0= RULE_STRING ) )
                    {
                    otherlv_12=(Token)match(input,66,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getDataAssetAccess().getFormatKeyword_12_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_13, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_12_1());
                    			
                    // InternalMlopsDsl.g:1635:4: ( (lv_format_14_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:1636:5: (lv_format_14_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:1636:5: (lv_format_14_0= RULE_STRING )
                    // InternalMlopsDsl.g:1637:6: lv_format_14_0= RULE_STRING
                    {
                    lv_format_14_0=(Token)match(input,RULE_STRING,FOLLOW_57); 

                    						newLeafNode(lv_format_14_0, grammarAccess.getDataAssetAccess().getFormatSTRINGTerminalRuleCall_12_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataAssetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"format",
                    							lv_format_14_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1654:3: (otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) ) )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==67) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // InternalMlopsDsl.g:1655:4: otherlv_15= 'schema' otherlv_16= '=' ( (lv_schema_17_0= RULE_STRING ) )
                    {
                    otherlv_15=(Token)match(input,67,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getDataAssetAccess().getSchemaKeyword_13_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_16, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_13_1());
                    			
                    // InternalMlopsDsl.g:1663:4: ( (lv_schema_17_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:1664:5: (lv_schema_17_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:1664:5: (lv_schema_17_0= RULE_STRING )
                    // InternalMlopsDsl.g:1665:6: lv_schema_17_0= RULE_STRING
                    {
                    lv_schema_17_0=(Token)match(input,RULE_STRING,FOLLOW_58); 

                    						newLeafNode(lv_schema_17_0, grammarAccess.getDataAssetAccess().getSchemaSTRINGTerminalRuleCall_13_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataAssetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"schema",
                    							lv_schema_17_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1682:3: (otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) ) )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==39) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // InternalMlopsDsl.g:1683:4: otherlv_18= 'version' otherlv_19= '=' ( (lv_version_20_0= RULE_STRING ) )
                    {
                    otherlv_18=(Token)match(input,39,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getDataAssetAccess().getVersionKeyword_14_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_19, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_14_1());
                    			
                    // InternalMlopsDsl.g:1691:4: ( (lv_version_20_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:1692:5: (lv_version_20_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:1692:5: (lv_version_20_0= RULE_STRING )
                    // InternalMlopsDsl.g:1693:6: lv_version_20_0= RULE_STRING
                    {
                    lv_version_20_0=(Token)match(input,RULE_STRING,FOLLOW_59); 

                    						newLeafNode(lv_version_20_0, grammarAccess.getDataAssetAccess().getVersionSTRINGTerminalRuleCall_14_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataAssetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"version",
                    							lv_version_20_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1710:3: (otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) ) )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==68) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // InternalMlopsDsl.g:1711:4: otherlv_21= 'sizeBytes' otherlv_22= '=' ( (lv_sizeBytes_23_0= ruleSignedLong ) )
                    {
                    otherlv_21=(Token)match(input,68,FOLLOW_6); 

                    				newLeafNode(otherlv_21, grammarAccess.getDataAssetAccess().getSizeBytesKeyword_15_0());
                    			
                    otherlv_22=(Token)match(input,15,FOLLOW_60); 

                    				newLeafNode(otherlv_22, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_15_1());
                    			
                    // InternalMlopsDsl.g:1719:4: ( (lv_sizeBytes_23_0= ruleSignedLong ) )
                    // InternalMlopsDsl.g:1720:5: (lv_sizeBytes_23_0= ruleSignedLong )
                    {
                    // InternalMlopsDsl.g:1720:5: (lv_sizeBytes_23_0= ruleSignedLong )
                    // InternalMlopsDsl.g:1721:6: lv_sizeBytes_23_0= ruleSignedLong
                    {

                    						newCompositeNode(grammarAccess.getDataAssetAccess().getSizeBytesSignedLongParserRuleCall_15_2_0());
                    					
                    pushFollow(FOLLOW_61);
                    lv_sizeBytes_23_0=ruleSignedLong();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDataAssetRule());
                    						}
                    						set(
                    							current,
                    							"sizeBytes",
                    							lv_sizeBytes_23_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.SignedLong");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1739:3: (otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) ) )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==69) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // InternalMlopsDsl.g:1740:4: otherlv_24= 'recordCount' otherlv_25= '=' ( (lv_recordCount_26_0= ruleSignedLong ) )
                    {
                    otherlv_24=(Token)match(input,69,FOLLOW_6); 

                    				newLeafNode(otherlv_24, grammarAccess.getDataAssetAccess().getRecordCountKeyword_16_0());
                    			
                    otherlv_25=(Token)match(input,15,FOLLOW_60); 

                    				newLeafNode(otherlv_25, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_16_1());
                    			
                    // InternalMlopsDsl.g:1748:4: ( (lv_recordCount_26_0= ruleSignedLong ) )
                    // InternalMlopsDsl.g:1749:5: (lv_recordCount_26_0= ruleSignedLong )
                    {
                    // InternalMlopsDsl.g:1749:5: (lv_recordCount_26_0= ruleSignedLong )
                    // InternalMlopsDsl.g:1750:6: lv_recordCount_26_0= ruleSignedLong
                    {

                    						newCompositeNode(grammarAccess.getDataAssetAccess().getRecordCountSignedLongParserRuleCall_16_2_0());
                    					
                    pushFollow(FOLLOW_62);
                    lv_recordCount_26_0=ruleSignedLong();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDataAssetRule());
                    						}
                    						set(
                    							current,
                    							"recordCount",
                    							lv_recordCount_26_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.SignedLong");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1768:3: (otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) ) )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==70) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // InternalMlopsDsl.g:1769:4: otherlv_27= 'license' otherlv_28= '=' ( (lv_license_29_0= RULE_STRING ) )
                    {
                    otherlv_27=(Token)match(input,70,FOLLOW_6); 

                    				newLeafNode(otherlv_27, grammarAccess.getDataAssetAccess().getLicenseKeyword_17_0());
                    			
                    otherlv_28=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_28, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_17_1());
                    			
                    // InternalMlopsDsl.g:1777:4: ( (lv_license_29_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:1778:5: (lv_license_29_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:1778:5: (lv_license_29_0= RULE_STRING )
                    // InternalMlopsDsl.g:1779:6: lv_license_29_0= RULE_STRING
                    {
                    lv_license_29_0=(Token)match(input,RULE_STRING,FOLLOW_63); 

                    						newLeafNode(lv_license_29_0, grammarAccess.getDataAssetAccess().getLicenseSTRINGTerminalRuleCall_17_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataAssetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"license",
                    							lv_license_29_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1796:3: (otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) ) )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==71) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // InternalMlopsDsl.g:1797:4: otherlv_30= 'privacy' otherlv_31= '=' ( (lv_privacy_32_0= RULE_STRING ) )
                    {
                    otherlv_30=(Token)match(input,71,FOLLOW_6); 

                    				newLeafNode(otherlv_30, grammarAccess.getDataAssetAccess().getPrivacyKeyword_18_0());
                    			
                    otherlv_31=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_31, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_18_1());
                    			
                    // InternalMlopsDsl.g:1805:4: ( (lv_privacy_32_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:1806:5: (lv_privacy_32_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:1806:5: (lv_privacy_32_0= RULE_STRING )
                    // InternalMlopsDsl.g:1807:6: lv_privacy_32_0= RULE_STRING
                    {
                    lv_privacy_32_0=(Token)match(input,RULE_STRING,FOLLOW_64); 

                    						newLeafNode(lv_privacy_32_0, grammarAccess.getDataAssetAccess().getPrivacySTRINGTerminalRuleCall_18_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataAssetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"privacy",
                    							lv_privacy_32_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1824:3: (otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) ) )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==72) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // InternalMlopsDsl.g:1825:4: otherlv_33= 'hash' otherlv_34= '=' ( (lv_hash_35_0= RULE_STRING ) )
                    {
                    otherlv_33=(Token)match(input,72,FOLLOW_6); 

                    				newLeafNode(otherlv_33, grammarAccess.getDataAssetAccess().getHashKeyword_19_0());
                    			
                    otherlv_34=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_34, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_19_1());
                    			
                    // InternalMlopsDsl.g:1833:4: ( (lv_hash_35_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:1834:5: (lv_hash_35_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:1834:5: (lv_hash_35_0= RULE_STRING )
                    // InternalMlopsDsl.g:1835:6: lv_hash_35_0= RULE_STRING
                    {
                    lv_hash_35_0=(Token)match(input,RULE_STRING,FOLLOW_65); 

                    						newLeafNode(lv_hash_35_0, grammarAccess.getDataAssetAccess().getHashSTRINGTerminalRuleCall_19_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDataAssetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"hash",
                    							lv_hash_35_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:1852:3: (otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']' )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==73) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // InternalMlopsDsl.g:1853:4: otherlv_36= 'splits' otherlv_37= '=' otherlv_38= '[' ( (lv_splits_39_0= ruleSplitSpec ) ) (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )* otherlv_42= ']'
                    {
                    otherlv_36=(Token)match(input,73,FOLLOW_6); 

                    				newLeafNode(otherlv_36, grammarAccess.getDataAssetAccess().getSplitsKeyword_20_0());
                    			
                    otherlv_37=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_37, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_20_1());
                    			
                    otherlv_38=(Token)match(input,35,FOLLOW_66); 

                    				newLeafNode(otherlv_38, grammarAccess.getDataAssetAccess().getLeftSquareBracketKeyword_20_2());
                    			
                    // InternalMlopsDsl.g:1865:4: ( (lv_splits_39_0= ruleSplitSpec ) )
                    // InternalMlopsDsl.g:1866:5: (lv_splits_39_0= ruleSplitSpec )
                    {
                    // InternalMlopsDsl.g:1866:5: (lv_splits_39_0= ruleSplitSpec )
                    // InternalMlopsDsl.g:1867:6: lv_splits_39_0= ruleSplitSpec
                    {

                    						newCompositeNode(grammarAccess.getDataAssetAccess().getSplitsSplitSpecParserRuleCall_20_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_splits_39_0=ruleSplitSpec();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDataAssetRule());
                    						}
                    						add(
                    							current,
                    							"splits",
                    							lv_splits_39_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.SplitSpec");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:1884:4: (otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) ) )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==36) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:1885:5: otherlv_40= ',' ( (lv_splits_41_0= ruleSplitSpec ) )
                    	    {
                    	    otherlv_40=(Token)match(input,36,FOLLOW_66); 

                    	    					newLeafNode(otherlv_40, grammarAccess.getDataAssetAccess().getCommaKeyword_20_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:1889:5: ( (lv_splits_41_0= ruleSplitSpec ) )
                    	    // InternalMlopsDsl.g:1890:6: (lv_splits_41_0= ruleSplitSpec )
                    	    {
                    	    // InternalMlopsDsl.g:1890:6: (lv_splits_41_0= ruleSplitSpec )
                    	    // InternalMlopsDsl.g:1891:7: lv_splits_41_0= ruleSplitSpec
                    	    {

                    	    							newCompositeNode(grammarAccess.getDataAssetAccess().getSplitsSplitSpecParserRuleCall_20_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_splits_41_0=ruleSplitSpec();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getDataAssetRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"splits",
                    	    								lv_splits_41_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.SplitSpec");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);

                    otherlv_42=(Token)match(input,37,FOLLOW_67); 

                    				newLeafNode(otherlv_42, grammarAccess.getDataAssetAccess().getRightSquareBracketKeyword_20_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:1914:3: (otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']' )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==74) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // InternalMlopsDsl.g:1915:4: otherlv_43= 'annotations' otherlv_44= '=' otherlv_45= '[' ( (lv_annotations_46_0= ruleAnnotationSpec ) ) (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )* otherlv_49= ']'
                    {
                    otherlv_43=(Token)match(input,74,FOLLOW_6); 

                    				newLeafNode(otherlv_43, grammarAccess.getDataAssetAccess().getAnnotationsKeyword_21_0());
                    			
                    otherlv_44=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_44, grammarAccess.getDataAssetAccess().getEqualsSignKeyword_21_1());
                    			
                    otherlv_45=(Token)match(input,35,FOLLOW_68); 

                    				newLeafNode(otherlv_45, grammarAccess.getDataAssetAccess().getLeftSquareBracketKeyword_21_2());
                    			
                    // InternalMlopsDsl.g:1927:4: ( (lv_annotations_46_0= ruleAnnotationSpec ) )
                    // InternalMlopsDsl.g:1928:5: (lv_annotations_46_0= ruleAnnotationSpec )
                    {
                    // InternalMlopsDsl.g:1928:5: (lv_annotations_46_0= ruleAnnotationSpec )
                    // InternalMlopsDsl.g:1929:6: lv_annotations_46_0= ruleAnnotationSpec
                    {

                    						newCompositeNode(grammarAccess.getDataAssetAccess().getAnnotationsAnnotationSpecParserRuleCall_21_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_annotations_46_0=ruleAnnotationSpec();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDataAssetRule());
                    						}
                    						add(
                    							current,
                    							"annotations",
                    							lv_annotations_46_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.AnnotationSpec");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:1946:4: (otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) ) )*
                    loop22:
                    do {
                        int alt22=2;
                        int LA22_0 = input.LA(1);

                        if ( (LA22_0==36) ) {
                            alt22=1;
                        }


                        switch (alt22) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:1947:5: otherlv_47= ',' ( (lv_annotations_48_0= ruleAnnotationSpec ) )
                    	    {
                    	    otherlv_47=(Token)match(input,36,FOLLOW_68); 

                    	    					newLeafNode(otherlv_47, grammarAccess.getDataAssetAccess().getCommaKeyword_21_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:1951:5: ( (lv_annotations_48_0= ruleAnnotationSpec ) )
                    	    // InternalMlopsDsl.g:1952:6: (lv_annotations_48_0= ruleAnnotationSpec )
                    	    {
                    	    // InternalMlopsDsl.g:1952:6: (lv_annotations_48_0= ruleAnnotationSpec )
                    	    // InternalMlopsDsl.g:1953:7: lv_annotations_48_0= ruleAnnotationSpec
                    	    {

                    	    							newCompositeNode(grammarAccess.getDataAssetAccess().getAnnotationsAnnotationSpecParserRuleCall_21_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_annotations_48_0=ruleAnnotationSpec();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getDataAssetRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"annotations",
                    	    								lv_annotations_48_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.AnnotationSpec");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop22;
                        }
                    } while (true);

                    otherlv_49=(Token)match(input,37,FOLLOW_8); 

                    				newLeafNode(otherlv_49, grammarAccess.getDataAssetAccess().getRightSquareBracketKeyword_21_5());
                    			

                    }
                    break;

            }

            otherlv_50=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_50, grammarAccess.getDataAssetAccess().getRightCurlyBracketKeyword_22());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDataAsset"


    // $ANTLR start "entryRuleSplitSpec"
    // InternalMlopsDsl.g:1984:1: entryRuleSplitSpec returns [EObject current=null] : iv_ruleSplitSpec= ruleSplitSpec EOF ;
    public final EObject entryRuleSplitSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSplitSpec = null;


        try {
            // InternalMlopsDsl.g:1984:50: (iv_ruleSplitSpec= ruleSplitSpec EOF )
            // InternalMlopsDsl.g:1985:2: iv_ruleSplitSpec= ruleSplitSpec EOF
            {
             newCompositeNode(grammarAccess.getSplitSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSplitSpec=ruleSplitSpec();

            state._fsp--;

             current =iv_ruleSplitSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSplitSpec"


    // $ANTLR start "ruleSplitSpec"
    // InternalMlopsDsl.g:1991:1: ruleSplitSpec returns [EObject current=null] : (otherlv_0= 'SplitSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'ratio' otherlv_6= '=' ( (lv_ratio_7_0= RULE_FLOAT ) ) otherlv_8= '}' ) ;
    public final EObject ruleSplitSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_ratio_7_0=null;
        Token otherlv_8=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:1997:2: ( (otherlv_0= 'SplitSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'ratio' otherlv_6= '=' ( (lv_ratio_7_0= RULE_FLOAT ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:1998:2: (otherlv_0= 'SplitSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'ratio' otherlv_6= '=' ( (lv_ratio_7_0= RULE_FLOAT ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:1998:2: (otherlv_0= 'SplitSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'ratio' otherlv_6= '=' ( (lv_ratio_7_0= RULE_FLOAT ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:1999:3: otherlv_0= 'SplitSpec' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'ratio' otherlv_6= '=' ( (lv_ratio_7_0= RULE_FLOAT ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,75,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getSplitSpecAccess().getSplitSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getSplitSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getSplitSpecAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getSplitSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:2015:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2016:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2016:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:2017:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_69); 

            					newLeafNode(lv_name_4_0, grammarAccess.getSplitSpecAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSplitSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,76,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getSplitSpecAccess().getRatioKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_70); 

            			newLeafNode(otherlv_6, grammarAccess.getSplitSpecAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:2041:3: ( (lv_ratio_7_0= RULE_FLOAT ) )
            // InternalMlopsDsl.g:2042:4: (lv_ratio_7_0= RULE_FLOAT )
            {
            // InternalMlopsDsl.g:2042:4: (lv_ratio_7_0= RULE_FLOAT )
            // InternalMlopsDsl.g:2043:5: lv_ratio_7_0= RULE_FLOAT
            {
            lv_ratio_7_0=(Token)match(input,RULE_FLOAT,FOLLOW_8); 

            					newLeafNode(lv_ratio_7_0, grammarAccess.getSplitSpecAccess().getRatioFLOATTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSplitSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"ratio",
            						lv_ratio_7_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getSplitSpecAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSplitSpec"


    // $ANTLR start "entryRuleAnnotationSpec"
    // InternalMlopsDsl.g:2067:1: entryRuleAnnotationSpec returns [EObject current=null] : iv_ruleAnnotationSpec= ruleAnnotationSpec EOF ;
    public final EObject entryRuleAnnotationSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleAnnotationSpec = null;


        try {
            // InternalMlopsDsl.g:2067:55: (iv_ruleAnnotationSpec= ruleAnnotationSpec EOF )
            // InternalMlopsDsl.g:2068:2: iv_ruleAnnotationSpec= ruleAnnotationSpec EOF
            {
             newCompositeNode(grammarAccess.getAnnotationSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleAnnotationSpec=ruleAnnotationSpec();

            state._fsp--;

             current =iv_ruleAnnotationSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleAnnotationSpec"


    // $ANTLR start "ruleAnnotationSpec"
    // InternalMlopsDsl.g:2074:1: ruleAnnotationSpec returns [EObject current=null] : (otherlv_0= 'AnnotationSpec' otherlv_1= '{' otherlv_2= 'labelSet' otherlv_3= '=' ( (lv_labelSet_4_0= RULE_STRING ) ) otherlv_5= 'guideline' otherlv_6= '=' ( (lv_guideline_7_0= RULE_STRING ) ) otherlv_8= '}' ) ;
    public final EObject ruleAnnotationSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_labelSet_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_guideline_7_0=null;
        Token otherlv_8=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:2080:2: ( (otherlv_0= 'AnnotationSpec' otherlv_1= '{' otherlv_2= 'labelSet' otherlv_3= '=' ( (lv_labelSet_4_0= RULE_STRING ) ) otherlv_5= 'guideline' otherlv_6= '=' ( (lv_guideline_7_0= RULE_STRING ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:2081:2: (otherlv_0= 'AnnotationSpec' otherlv_1= '{' otherlv_2= 'labelSet' otherlv_3= '=' ( (lv_labelSet_4_0= RULE_STRING ) ) otherlv_5= 'guideline' otherlv_6= '=' ( (lv_guideline_7_0= RULE_STRING ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:2081:2: (otherlv_0= 'AnnotationSpec' otherlv_1= '{' otherlv_2= 'labelSet' otherlv_3= '=' ( (lv_labelSet_4_0= RULE_STRING ) ) otherlv_5= 'guideline' otherlv_6= '=' ( (lv_guideline_7_0= RULE_STRING ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:2082:3: otherlv_0= 'AnnotationSpec' otherlv_1= '{' otherlv_2= 'labelSet' otherlv_3= '=' ( (lv_labelSet_4_0= RULE_STRING ) ) otherlv_5= 'guideline' otherlv_6= '=' ( (lv_guideline_7_0= RULE_STRING ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,77,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getAnnotationSpecAccess().getAnnotationSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_71); 

            			newLeafNode(otherlv_1, grammarAccess.getAnnotationSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,78,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getAnnotationSpecAccess().getLabelSetKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getAnnotationSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:2098:3: ( (lv_labelSet_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2099:4: (lv_labelSet_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2099:4: (lv_labelSet_4_0= RULE_STRING )
            // InternalMlopsDsl.g:2100:5: lv_labelSet_4_0= RULE_STRING
            {
            lv_labelSet_4_0=(Token)match(input,RULE_STRING,FOLLOW_72); 

            					newLeafNode(lv_labelSet_4_0, grammarAccess.getAnnotationSpecAccess().getLabelSetSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAnnotationSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"labelSet",
            						lv_labelSet_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,79,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getAnnotationSpecAccess().getGuidelineKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_6, grammarAccess.getAnnotationSpecAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:2124:3: ( (lv_guideline_7_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2125:4: (lv_guideline_7_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2125:4: (lv_guideline_7_0= RULE_STRING )
            // InternalMlopsDsl.g:2126:5: lv_guideline_7_0= RULE_STRING
            {
            lv_guideline_7_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_guideline_7_0, grammarAccess.getAnnotationSpecAccess().getGuidelineSTRINGTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getAnnotationSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"guideline",
            						lv_guideline_7_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getAnnotationSpecAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleAnnotationSpec"


    // $ANTLR start "entryRuleFeatureSet"
    // InternalMlopsDsl.g:2150:1: entryRuleFeatureSet returns [EObject current=null] : iv_ruleFeatureSet= ruleFeatureSet EOF ;
    public final EObject entryRuleFeatureSet() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeatureSet = null;


        try {
            // InternalMlopsDsl.g:2150:51: (iv_ruleFeatureSet= ruleFeatureSet EOF )
            // InternalMlopsDsl.g:2151:2: iv_ruleFeatureSet= ruleFeatureSet EOF
            {
             newCompositeNode(grammarAccess.getFeatureSetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFeatureSet=ruleFeatureSet();

            state._fsp--;

             current =iv_ruleFeatureSet; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFeatureSet"


    // $ANTLR start "ruleFeatureSet"
    // InternalMlopsDsl.g:2157:1: ruleFeatureSet returns [EObject current=null] : (otherlv_0= 'FeatureSet' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'name' otherlv_4= '=' ( (lv_name_5_0= RULE_STRING ) ) otherlv_6= '}' ) ;
    public final EObject ruleFeatureSet() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_name_5_0=null;
        Token otherlv_6=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:2163:2: ( (otherlv_0= 'FeatureSet' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'name' otherlv_4= '=' ( (lv_name_5_0= RULE_STRING ) ) otherlv_6= '}' ) )
            // InternalMlopsDsl.g:2164:2: (otherlv_0= 'FeatureSet' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'name' otherlv_4= '=' ( (lv_name_5_0= RULE_STRING ) ) otherlv_6= '}' )
            {
            // InternalMlopsDsl.g:2164:2: (otherlv_0= 'FeatureSet' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'name' otherlv_4= '=' ( (lv_name_5_0= RULE_STRING ) ) otherlv_6= '}' )
            // InternalMlopsDsl.g:2165:3: otherlv_0= 'FeatureSet' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'name' otherlv_4= '=' ( (lv_name_5_0= RULE_STRING ) ) otherlv_6= '}'
            {
            otherlv_0=(Token)match(input,80,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getFeatureSetAccess().getFeatureSetKeyword_0());
            		
            // InternalMlopsDsl.g:2169:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:2170:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:2170:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:2171:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getFeatureSetAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFeatureSetRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_2, grammarAccess.getFeatureSetAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getFeatureSetAccess().getNameKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_4, grammarAccess.getFeatureSetAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:2199:3: ( (lv_name_5_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2200:4: (lv_name_5_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2200:4: (lv_name_5_0= RULE_STRING )
            // InternalMlopsDsl.g:2201:5: lv_name_5_0= RULE_STRING
            {
            lv_name_5_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_name_5_0, grammarAccess.getFeatureSetAccess().getNameSTRINGTerminalRuleCall_5_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFeatureSetRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_5_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_6=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_6, grammarAccess.getFeatureSetAccess().getRightCurlyBracketKeyword_6());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFeatureSet"


    // $ANTLR start "entryRuleTrainingSpec"
    // InternalMlopsDsl.g:2225:1: entryRuleTrainingSpec returns [EObject current=null] : iv_ruleTrainingSpec= ruleTrainingSpec EOF ;
    public final EObject entryRuleTrainingSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleTrainingSpec = null;


        try {
            // InternalMlopsDsl.g:2225:53: (iv_ruleTrainingSpec= ruleTrainingSpec EOF )
            // InternalMlopsDsl.g:2226:2: iv_ruleTrainingSpec= ruleTrainingSpec EOF
            {
             newCompositeNode(grammarAccess.getTrainingSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleTrainingSpec=ruleTrainingSpec();

            state._fsp--;

             current =iv_ruleTrainingSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleTrainingSpec"


    // $ANTLR start "ruleTrainingSpec"
    // InternalMlopsDsl.g:2232:1: ruleTrainingSpec returns [EObject current=null] : (otherlv_0= 'TrainingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'framework' otherlv_4= '=' ( (lv_framework_5_0= RULE_STRING ) ) (otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) ) )? (otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']' )? (otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) ) )? (otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']' )? (otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']' )? (otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']' )? otherlv_40= '}' ) ;
    public final EObject ruleTrainingSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_framework_5_0=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_algorithm_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token lv_image_18_0=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token lv_command_22_0=null;
        Token otherlv_23=null;
        Token lv_command_24_0=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_32=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_39=null;
        Token otherlv_40=null;
        EObject lv_hyperparams_12_0 = null;

        EObject lv_hyperparams_14_0 = null;

        EObject lv_resources_29_0 = null;

        EObject lv_resources_31_0 = null;

        EObject lv_env_36_0 = null;

        EObject lv_env_38_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:2238:2: ( (otherlv_0= 'TrainingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'framework' otherlv_4= '=' ( (lv_framework_5_0= RULE_STRING ) ) (otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) ) )? (otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']' )? (otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) ) )? (otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']' )? (otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']' )? (otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']' )? otherlv_40= '}' ) )
            // InternalMlopsDsl.g:2239:2: (otherlv_0= 'TrainingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'framework' otherlv_4= '=' ( (lv_framework_5_0= RULE_STRING ) ) (otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) ) )? (otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']' )? (otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) ) )? (otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']' )? (otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']' )? (otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']' )? otherlv_40= '}' )
            {
            // InternalMlopsDsl.g:2239:2: (otherlv_0= 'TrainingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'framework' otherlv_4= '=' ( (lv_framework_5_0= RULE_STRING ) ) (otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) ) )? (otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']' )? (otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) ) )? (otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']' )? (otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']' )? (otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']' )? otherlv_40= '}' )
            // InternalMlopsDsl.g:2240:3: otherlv_0= 'TrainingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'framework' otherlv_4= '=' ( (lv_framework_5_0= RULE_STRING ) ) (otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) ) )? (otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']' )? (otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) ) )? (otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']' )? (otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']' )? (otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']' )? otherlv_40= '}'
            {
            otherlv_0=(Token)match(input,81,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getTrainingSpecAccess().getTrainingSpecKeyword_0());
            		
            // InternalMlopsDsl.g:2244:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:2245:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:2245:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:2246:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getTrainingSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTrainingSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_73); 

            			newLeafNode(otherlv_2, grammarAccess.getTrainingSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,82,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getTrainingSpecAccess().getFrameworkKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_4, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:2274:3: ( (lv_framework_5_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2275:4: (lv_framework_5_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2275:4: (lv_framework_5_0= RULE_STRING )
            // InternalMlopsDsl.g:2276:5: lv_framework_5_0= RULE_STRING
            {
            lv_framework_5_0=(Token)match(input,RULE_STRING,FOLLOW_74); 

            					newLeafNode(lv_framework_5_0, grammarAccess.getTrainingSpecAccess().getFrameworkSTRINGTerminalRuleCall_5_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getTrainingSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"framework",
            						lv_framework_5_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:2292:3: (otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) ) )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==83) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // InternalMlopsDsl.g:2293:4: otherlv_6= 'algorithm' otherlv_7= '=' ( (lv_algorithm_8_0= RULE_STRING ) )
                    {
                    otherlv_6=(Token)match(input,83,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getTrainingSpecAccess().getAlgorithmKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_7, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:2301:4: ( (lv_algorithm_8_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:2302:5: (lv_algorithm_8_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:2302:5: (lv_algorithm_8_0= RULE_STRING )
                    // InternalMlopsDsl.g:2303:6: lv_algorithm_8_0= RULE_STRING
                    {
                    lv_algorithm_8_0=(Token)match(input,RULE_STRING,FOLLOW_75); 

                    						newLeafNode(lv_algorithm_8_0, grammarAccess.getTrainingSpecAccess().getAlgorithmSTRINGTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTrainingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"algorithm",
                    							lv_algorithm_8_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:2320:3: (otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']' )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==84) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // InternalMlopsDsl.g:2321:4: otherlv_9= 'hyperparams' otherlv_10= '=' otherlv_11= '[' ( (lv_hyperparams_12_0= ruleParam ) ) (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )* otherlv_15= ']'
                    {
                    otherlv_9=(Token)match(input,84,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getTrainingSpecAccess().getHyperparamsKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_10, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_7_1());
                    			
                    otherlv_11=(Token)match(input,35,FOLLOW_76); 

                    				newLeafNode(otherlv_11, grammarAccess.getTrainingSpecAccess().getLeftSquareBracketKeyword_7_2());
                    			
                    // InternalMlopsDsl.g:2333:4: ( (lv_hyperparams_12_0= ruleParam ) )
                    // InternalMlopsDsl.g:2334:5: (lv_hyperparams_12_0= ruleParam )
                    {
                    // InternalMlopsDsl.g:2334:5: (lv_hyperparams_12_0= ruleParam )
                    // InternalMlopsDsl.g:2335:6: lv_hyperparams_12_0= ruleParam
                    {

                    						newCompositeNode(grammarAccess.getTrainingSpecAccess().getHyperparamsParamParserRuleCall_7_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_hyperparams_12_0=ruleParam();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTrainingSpecRule());
                    						}
                    						add(
                    							current,
                    							"hyperparams",
                    							lv_hyperparams_12_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:2352:4: (otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) ) )*
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( (LA25_0==36) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:2353:5: otherlv_13= ',' ( (lv_hyperparams_14_0= ruleParam ) )
                    	    {
                    	    otherlv_13=(Token)match(input,36,FOLLOW_76); 

                    	    					newLeafNode(otherlv_13, grammarAccess.getTrainingSpecAccess().getCommaKeyword_7_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:2357:5: ( (lv_hyperparams_14_0= ruleParam ) )
                    	    // InternalMlopsDsl.g:2358:6: (lv_hyperparams_14_0= ruleParam )
                    	    {
                    	    // InternalMlopsDsl.g:2358:6: (lv_hyperparams_14_0= ruleParam )
                    	    // InternalMlopsDsl.g:2359:7: lv_hyperparams_14_0= ruleParam
                    	    {

                    	    							newCompositeNode(grammarAccess.getTrainingSpecAccess().getHyperparamsParamParserRuleCall_7_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_hyperparams_14_0=ruleParam();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getTrainingSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"hyperparams",
                    	    								lv_hyperparams_14_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop25;
                        }
                    } while (true);

                    otherlv_15=(Token)match(input,37,FOLLOW_77); 

                    				newLeafNode(otherlv_15, grammarAccess.getTrainingSpecAccess().getRightSquareBracketKeyword_7_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:2382:3: (otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) ) )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==85) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // InternalMlopsDsl.g:2383:4: otherlv_16= 'image' otherlv_17= '=' ( (lv_image_18_0= RULE_STRING ) )
                    {
                    otherlv_16=(Token)match(input,85,FOLLOW_6); 

                    				newLeafNode(otherlv_16, grammarAccess.getTrainingSpecAccess().getImageKeyword_8_0());
                    			
                    otherlv_17=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_17, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:2391:4: ( (lv_image_18_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:2392:5: (lv_image_18_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:2392:5: (lv_image_18_0= RULE_STRING )
                    // InternalMlopsDsl.g:2393:6: lv_image_18_0= RULE_STRING
                    {
                    lv_image_18_0=(Token)match(input,RULE_STRING,FOLLOW_78); 

                    						newLeafNode(lv_image_18_0, grammarAccess.getTrainingSpecAccess().getImageSTRINGTerminalRuleCall_8_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTrainingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"image",
                    							lv_image_18_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:2410:3: (otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']' )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==86) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // InternalMlopsDsl.g:2411:4: otherlv_19= 'command' otherlv_20= '=' otherlv_21= '[' ( (lv_command_22_0= RULE_STRING ) ) (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )* otherlv_25= ']'
                    {
                    otherlv_19=(Token)match(input,86,FOLLOW_6); 

                    				newLeafNode(otherlv_19, grammarAccess.getTrainingSpecAccess().getCommandKeyword_9_0());
                    			
                    otherlv_20=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_20, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    otherlv_21=(Token)match(input,35,FOLLOW_7); 

                    				newLeafNode(otherlv_21, grammarAccess.getTrainingSpecAccess().getLeftSquareBracketKeyword_9_2());
                    			
                    // InternalMlopsDsl.g:2423:4: ( (lv_command_22_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:2424:5: (lv_command_22_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:2424:5: (lv_command_22_0= RULE_STRING )
                    // InternalMlopsDsl.g:2425:6: lv_command_22_0= RULE_STRING
                    {
                    lv_command_22_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    						newLeafNode(lv_command_22_0, grammarAccess.getTrainingSpecAccess().getCommandSTRINGTerminalRuleCall_9_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getTrainingSpecRule());
                    						}
                    						addWithLastConsumed(
                    							current,
                    							"command",
                    							lv_command_22_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }

                    // InternalMlopsDsl.g:2441:4: (otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) ) )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( (LA28_0==36) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:2442:5: otherlv_23= ',' ( (lv_command_24_0= RULE_STRING ) )
                    	    {
                    	    otherlv_23=(Token)match(input,36,FOLLOW_7); 

                    	    					newLeafNode(otherlv_23, grammarAccess.getTrainingSpecAccess().getCommaKeyword_9_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:2446:5: ( (lv_command_24_0= RULE_STRING ) )
                    	    // InternalMlopsDsl.g:2447:6: (lv_command_24_0= RULE_STRING )
                    	    {
                    	    // InternalMlopsDsl.g:2447:6: (lv_command_24_0= RULE_STRING )
                    	    // InternalMlopsDsl.g:2448:7: lv_command_24_0= RULE_STRING
                    	    {
                    	    lv_command_24_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    	    							newLeafNode(lv_command_24_0, grammarAccess.getTrainingSpecAccess().getCommandSTRINGTerminalRuleCall_9_4_1_0());
                    	    						

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getTrainingSpecRule());
                    	    							}
                    	    							addWithLastConsumed(
                    	    								current,
                    	    								"command",
                    	    								lv_command_24_0,
                    	    								"org.eclipse.xtext.common.Terminals.STRING");
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);

                    otherlv_25=(Token)match(input,37,FOLLOW_79); 

                    				newLeafNode(otherlv_25, grammarAccess.getTrainingSpecAccess().getRightSquareBracketKeyword_9_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:2470:3: (otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']' )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==87) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // InternalMlopsDsl.g:2471:4: otherlv_26= 'resources' otherlv_27= '=' otherlv_28= '[' ( (lv_resources_29_0= ruleResource ) ) (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )* otherlv_32= ']'
                    {
                    otherlv_26=(Token)match(input,87,FOLLOW_6); 

                    				newLeafNode(otherlv_26, grammarAccess.getTrainingSpecAccess().getResourcesKeyword_10_0());
                    			
                    otherlv_27=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_27, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    otherlv_28=(Token)match(input,35,FOLLOW_80); 

                    				newLeafNode(otherlv_28, grammarAccess.getTrainingSpecAccess().getLeftSquareBracketKeyword_10_2());
                    			
                    // InternalMlopsDsl.g:2483:4: ( (lv_resources_29_0= ruleResource ) )
                    // InternalMlopsDsl.g:2484:5: (lv_resources_29_0= ruleResource )
                    {
                    // InternalMlopsDsl.g:2484:5: (lv_resources_29_0= ruleResource )
                    // InternalMlopsDsl.g:2485:6: lv_resources_29_0= ruleResource
                    {

                    						newCompositeNode(grammarAccess.getTrainingSpecAccess().getResourcesResourceParserRuleCall_10_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_resources_29_0=ruleResource();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTrainingSpecRule());
                    						}
                    						add(
                    							current,
                    							"resources",
                    							lv_resources_29_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Resource");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:2502:4: (otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) ) )*
                    loop30:
                    do {
                        int alt30=2;
                        int LA30_0 = input.LA(1);

                        if ( (LA30_0==36) ) {
                            alt30=1;
                        }


                        switch (alt30) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:2503:5: otherlv_30= ',' ( (lv_resources_31_0= ruleResource ) )
                    	    {
                    	    otherlv_30=(Token)match(input,36,FOLLOW_80); 

                    	    					newLeafNode(otherlv_30, grammarAccess.getTrainingSpecAccess().getCommaKeyword_10_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:2507:5: ( (lv_resources_31_0= ruleResource ) )
                    	    // InternalMlopsDsl.g:2508:6: (lv_resources_31_0= ruleResource )
                    	    {
                    	    // InternalMlopsDsl.g:2508:6: (lv_resources_31_0= ruleResource )
                    	    // InternalMlopsDsl.g:2509:7: lv_resources_31_0= ruleResource
                    	    {

                    	    							newCompositeNode(grammarAccess.getTrainingSpecAccess().getResourcesResourceParserRuleCall_10_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_resources_31_0=ruleResource();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getTrainingSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"resources",
                    	    								lv_resources_31_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Resource");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop30;
                        }
                    } while (true);

                    otherlv_32=(Token)match(input,37,FOLLOW_81); 

                    				newLeafNode(otherlv_32, grammarAccess.getTrainingSpecAccess().getRightSquareBracketKeyword_10_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:2532:3: (otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']' )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==88) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // InternalMlopsDsl.g:2533:4: otherlv_33= 'env' otherlv_34= '=' otherlv_35= '[' ( (lv_env_36_0= ruleParam ) ) (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )* otherlv_39= ']'
                    {
                    otherlv_33=(Token)match(input,88,FOLLOW_6); 

                    				newLeafNode(otherlv_33, grammarAccess.getTrainingSpecAccess().getEnvKeyword_11_0());
                    			
                    otherlv_34=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_34, grammarAccess.getTrainingSpecAccess().getEqualsSignKeyword_11_1());
                    			
                    otherlv_35=(Token)match(input,35,FOLLOW_76); 

                    				newLeafNode(otherlv_35, grammarAccess.getTrainingSpecAccess().getLeftSquareBracketKeyword_11_2());
                    			
                    // InternalMlopsDsl.g:2545:4: ( (lv_env_36_0= ruleParam ) )
                    // InternalMlopsDsl.g:2546:5: (lv_env_36_0= ruleParam )
                    {
                    // InternalMlopsDsl.g:2546:5: (lv_env_36_0= ruleParam )
                    // InternalMlopsDsl.g:2547:6: lv_env_36_0= ruleParam
                    {

                    						newCompositeNode(grammarAccess.getTrainingSpecAccess().getEnvParamParserRuleCall_11_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_env_36_0=ruleParam();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getTrainingSpecRule());
                    						}
                    						add(
                    							current,
                    							"env",
                    							lv_env_36_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:2564:4: (otherlv_37= ',' ( (lv_env_38_0= ruleParam ) ) )*
                    loop32:
                    do {
                        int alt32=2;
                        int LA32_0 = input.LA(1);

                        if ( (LA32_0==36) ) {
                            alt32=1;
                        }


                        switch (alt32) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:2565:5: otherlv_37= ',' ( (lv_env_38_0= ruleParam ) )
                    	    {
                    	    otherlv_37=(Token)match(input,36,FOLLOW_76); 

                    	    					newLeafNode(otherlv_37, grammarAccess.getTrainingSpecAccess().getCommaKeyword_11_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:2569:5: ( (lv_env_38_0= ruleParam ) )
                    	    // InternalMlopsDsl.g:2570:6: (lv_env_38_0= ruleParam )
                    	    {
                    	    // InternalMlopsDsl.g:2570:6: (lv_env_38_0= ruleParam )
                    	    // InternalMlopsDsl.g:2571:7: lv_env_38_0= ruleParam
                    	    {

                    	    							newCompositeNode(grammarAccess.getTrainingSpecAccess().getEnvParamParserRuleCall_11_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_env_38_0=ruleParam();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getTrainingSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"env",
                    	    								lv_env_38_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop32;
                        }
                    } while (true);

                    otherlv_39=(Token)match(input,37,FOLLOW_8); 

                    				newLeafNode(otherlv_39, grammarAccess.getTrainingSpecAccess().getRightSquareBracketKeyword_11_5());
                    			

                    }
                    break;

            }

            otherlv_40=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_40, grammarAccess.getTrainingSpecAccess().getRightCurlyBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrainingSpec"


    // $ANTLR start "entryRuleThreshold"
    // InternalMlopsDsl.g:2602:1: entryRuleThreshold returns [EObject current=null] : iv_ruleThreshold= ruleThreshold EOF ;
    public final EObject entryRuleThreshold() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleThreshold = null;


        try {
            // InternalMlopsDsl.g:2602:50: (iv_ruleThreshold= ruleThreshold EOF )
            // InternalMlopsDsl.g:2603:2: iv_ruleThreshold= ruleThreshold EOF
            {
             newCompositeNode(grammarAccess.getThresholdRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleThreshold=ruleThreshold();

            state._fsp--;

             current =iv_ruleThreshold; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleThreshold"


    // $ANTLR start "ruleThreshold"
    // InternalMlopsDsl.g:2609:1: ruleThreshold returns [EObject current=null] : (otherlv_0= 'Threshold' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_FLOAT ) ) (otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) ) )? otherlv_11= '}' ) ;
    public final EObject ruleThreshold() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_key_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_value_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Enumerator lv_operator_10_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:2615:2: ( (otherlv_0= 'Threshold' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_FLOAT ) ) (otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) ) )? otherlv_11= '}' ) )
            // InternalMlopsDsl.g:2616:2: (otherlv_0= 'Threshold' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_FLOAT ) ) (otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) ) )? otherlv_11= '}' )
            {
            // InternalMlopsDsl.g:2616:2: (otherlv_0= 'Threshold' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_FLOAT ) ) (otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) ) )? otherlv_11= '}' )
            // InternalMlopsDsl.g:2617:3: otherlv_0= 'Threshold' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_FLOAT ) ) (otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) ) )? otherlv_11= '}'
            {
            otherlv_0=(Token)match(input,89,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getThresholdAccess().getThresholdKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getThresholdAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,20,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getThresholdAccess().getKeyKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getThresholdAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:2633:3: ( (lv_key_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2634:4: (lv_key_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2634:4: (lv_key_4_0= RULE_STRING )
            // InternalMlopsDsl.g:2635:5: lv_key_4_0= RULE_STRING
            {
            lv_key_4_0=(Token)match(input,RULE_STRING,FOLLOW_82); 

            					newLeafNode(lv_key_4_0, grammarAccess.getThresholdAccess().getKeySTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThresholdRule());
            					}
            					setWithLastConsumed(
            						current,
            						"key",
            						lv_key_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,90,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getThresholdAccess().getValueKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_70); 

            			newLeafNode(otherlv_6, grammarAccess.getThresholdAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:2659:3: ( (lv_value_7_0= RULE_FLOAT ) )
            // InternalMlopsDsl.g:2660:4: (lv_value_7_0= RULE_FLOAT )
            {
            // InternalMlopsDsl.g:2660:4: (lv_value_7_0= RULE_FLOAT )
            // InternalMlopsDsl.g:2661:5: lv_value_7_0= RULE_FLOAT
            {
            lv_value_7_0=(Token)match(input,RULE_FLOAT,FOLLOW_83); 

            					newLeafNode(lv_value_7_0, grammarAccess.getThresholdAccess().getValueFLOATTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getThresholdRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_7_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
            				

            }


            }

            // InternalMlopsDsl.g:2677:3: (otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) ) )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==91) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // InternalMlopsDsl.g:2678:4: otherlv_8= 'op' otherlv_9= '=' ( (lv_operator_10_0= ruleCompOp ) )
                    {
                    otherlv_8=(Token)match(input,91,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getThresholdAccess().getOpKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_84); 

                    				newLeafNode(otherlv_9, grammarAccess.getThresholdAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:2686:4: ( (lv_operator_10_0= ruleCompOp ) )
                    // InternalMlopsDsl.g:2687:5: (lv_operator_10_0= ruleCompOp )
                    {
                    // InternalMlopsDsl.g:2687:5: (lv_operator_10_0= ruleCompOp )
                    // InternalMlopsDsl.g:2688:6: lv_operator_10_0= ruleCompOp
                    {

                    						newCompositeNode(grammarAccess.getThresholdAccess().getOperatorCompOpEnumRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_operator_10_0=ruleCompOp();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getThresholdRule());
                    						}
                    						set(
                    							current,
                    							"operator",
                    							lv_operator_10_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.CompOp");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_11=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_11, grammarAccess.getThresholdAccess().getRightCurlyBracketKeyword_9());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleThreshold"


    // $ANTLR start "entryRuleEvaluationSpec"
    // InternalMlopsDsl.g:2714:1: entryRuleEvaluationSpec returns [EObject current=null] : iv_ruleEvaluationSpec= ruleEvaluationSpec EOF ;
    public final EObject entryRuleEvaluationSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEvaluationSpec = null;


        try {
            // InternalMlopsDsl.g:2714:55: (iv_ruleEvaluationSpec= ruleEvaluationSpec EOF )
            // InternalMlopsDsl.g:2715:2: iv_ruleEvaluationSpec= ruleEvaluationSpec EOF
            {
             newCompositeNode(grammarAccess.getEvaluationSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEvaluationSpec=ruleEvaluationSpec();

            state._fsp--;

             current =iv_ruleEvaluationSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEvaluationSpec"


    // $ANTLR start "ruleEvaluationSpec"
    // InternalMlopsDsl.g:2721:1: ruleEvaluationSpec returns [EObject current=null] : (otherlv_0= 'EvaluationSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'metrics' otherlv_4= '=' otherlv_5= '[' ( (lv_metrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' (otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']' )? otherlv_17= '}' ) ;
    public final EObject ruleEvaluationSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_metrics_6_0=null;
        Token otherlv_7=null;
        Token lv_metrics_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        EObject lv_thresholds_13_0 = null;

        EObject lv_thresholds_15_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:2727:2: ( (otherlv_0= 'EvaluationSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'metrics' otherlv_4= '=' otherlv_5= '[' ( (lv_metrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' (otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']' )? otherlv_17= '}' ) )
            // InternalMlopsDsl.g:2728:2: (otherlv_0= 'EvaluationSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'metrics' otherlv_4= '=' otherlv_5= '[' ( (lv_metrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' (otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']' )? otherlv_17= '}' )
            {
            // InternalMlopsDsl.g:2728:2: (otherlv_0= 'EvaluationSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'metrics' otherlv_4= '=' otherlv_5= '[' ( (lv_metrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' (otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']' )? otherlv_17= '}' )
            // InternalMlopsDsl.g:2729:3: otherlv_0= 'EvaluationSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'metrics' otherlv_4= '=' otherlv_5= '[' ( (lv_metrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' (otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']' )? otherlv_17= '}'
            {
            otherlv_0=(Token)match(input,92,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getEvaluationSpecAccess().getEvaluationSpecKeyword_0());
            		
            // InternalMlopsDsl.g:2733:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:2734:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:2734:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:2735:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getEvaluationSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEvaluationSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_85); 

            			newLeafNode(otherlv_2, grammarAccess.getEvaluationSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,93,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getEvaluationSpecAccess().getMetricsKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_4, grammarAccess.getEvaluationSpecAccess().getEqualsSignKeyword_4());
            		
            otherlv_5=(Token)match(input,35,FOLLOW_7); 

            			newLeafNode(otherlv_5, grammarAccess.getEvaluationSpecAccess().getLeftSquareBracketKeyword_5());
            		
            // InternalMlopsDsl.g:2767:3: ( (lv_metrics_6_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2768:4: (lv_metrics_6_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2768:4: (lv_metrics_6_0= RULE_STRING )
            // InternalMlopsDsl.g:2769:5: lv_metrics_6_0= RULE_STRING
            {
            lv_metrics_6_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

            					newLeafNode(lv_metrics_6_0, grammarAccess.getEvaluationSpecAccess().getMetricsSTRINGTerminalRuleCall_6_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEvaluationSpecRule());
            					}
            					addWithLastConsumed(
            						current,
            						"metrics",
            						lv_metrics_6_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:2785:3: (otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) ) )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==36) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // InternalMlopsDsl.g:2786:4: otherlv_7= ',' ( (lv_metrics_8_0= RULE_STRING ) )
            	    {
            	    otherlv_7=(Token)match(input,36,FOLLOW_7); 

            	    				newLeafNode(otherlv_7, grammarAccess.getEvaluationSpecAccess().getCommaKeyword_7_0());
            	    			
            	    // InternalMlopsDsl.g:2790:4: ( (lv_metrics_8_0= RULE_STRING ) )
            	    // InternalMlopsDsl.g:2791:5: (lv_metrics_8_0= RULE_STRING )
            	    {
            	    // InternalMlopsDsl.g:2791:5: (lv_metrics_8_0= RULE_STRING )
            	    // InternalMlopsDsl.g:2792:6: lv_metrics_8_0= RULE_STRING
            	    {
            	    lv_metrics_8_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

            	    						newLeafNode(lv_metrics_8_0, grammarAccess.getEvaluationSpecAccess().getMetricsSTRINGTerminalRuleCall_7_1_0());
            	    					

            	    						if (current==null) {
            	    							current = createModelElement(grammarAccess.getEvaluationSpecRule());
            	    						}
            	    						addWithLastConsumed(
            	    							current,
            	    							"metrics",
            	    							lv_metrics_8_0,
            	    							"org.eclipse.xtext.common.Terminals.STRING");
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            otherlv_9=(Token)match(input,37,FOLLOW_86); 

            			newLeafNode(otherlv_9, grammarAccess.getEvaluationSpecAccess().getRightSquareBracketKeyword_8());
            		
            // InternalMlopsDsl.g:2813:3: (otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']' )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==94) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // InternalMlopsDsl.g:2814:4: otherlv_10= 'thresholds' otherlv_11= '=' otherlv_12= '[' ( (lv_thresholds_13_0= ruleThreshold ) ) (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )* otherlv_16= ']'
                    {
                    otherlv_10=(Token)match(input,94,FOLLOW_6); 

                    				newLeafNode(otherlv_10, grammarAccess.getEvaluationSpecAccess().getThresholdsKeyword_9_0());
                    			
                    otherlv_11=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_11, grammarAccess.getEvaluationSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    otherlv_12=(Token)match(input,35,FOLLOW_87); 

                    				newLeafNode(otherlv_12, grammarAccess.getEvaluationSpecAccess().getLeftSquareBracketKeyword_9_2());
                    			
                    // InternalMlopsDsl.g:2826:4: ( (lv_thresholds_13_0= ruleThreshold ) )
                    // InternalMlopsDsl.g:2827:5: (lv_thresholds_13_0= ruleThreshold )
                    {
                    // InternalMlopsDsl.g:2827:5: (lv_thresholds_13_0= ruleThreshold )
                    // InternalMlopsDsl.g:2828:6: lv_thresholds_13_0= ruleThreshold
                    {

                    						newCompositeNode(grammarAccess.getEvaluationSpecAccess().getThresholdsThresholdParserRuleCall_9_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_thresholds_13_0=ruleThreshold();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getEvaluationSpecRule());
                    						}
                    						add(
                    							current,
                    							"thresholds",
                    							lv_thresholds_13_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Threshold");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:2845:4: (otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) ) )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0==36) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:2846:5: otherlv_14= ',' ( (lv_thresholds_15_0= ruleThreshold ) )
                    	    {
                    	    otherlv_14=(Token)match(input,36,FOLLOW_87); 

                    	    					newLeafNode(otherlv_14, grammarAccess.getEvaluationSpecAccess().getCommaKeyword_9_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:2850:5: ( (lv_thresholds_15_0= ruleThreshold ) )
                    	    // InternalMlopsDsl.g:2851:6: (lv_thresholds_15_0= ruleThreshold )
                    	    {
                    	    // InternalMlopsDsl.g:2851:6: (lv_thresholds_15_0= ruleThreshold )
                    	    // InternalMlopsDsl.g:2852:7: lv_thresholds_15_0= ruleThreshold
                    	    {

                    	    							newCompositeNode(grammarAccess.getEvaluationSpecAccess().getThresholdsThresholdParserRuleCall_9_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_thresholds_15_0=ruleThreshold();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getEvaluationSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"thresholds",
                    	    								lv_thresholds_15_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Threshold");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    otherlv_16=(Token)match(input,37,FOLLOW_8); 

                    				newLeafNode(otherlv_16, grammarAccess.getEvaluationSpecAccess().getRightSquareBracketKeyword_9_5());
                    			

                    }
                    break;

            }

            otherlv_17=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_17, grammarAccess.getEvaluationSpecAccess().getRightCurlyBracketKeyword_10());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEvaluationSpec"


    // $ANTLR start "entryRuleRegistrySpec"
    // InternalMlopsDsl.g:2883:1: entryRuleRegistrySpec returns [EObject current=null] : iv_ruleRegistrySpec= ruleRegistrySpec EOF ;
    public final EObject entryRuleRegistrySpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRegistrySpec = null;


        try {
            // InternalMlopsDsl.g:2883:53: (iv_ruleRegistrySpec= ruleRegistrySpec EOF )
            // InternalMlopsDsl.g:2884:2: iv_ruleRegistrySpec= ruleRegistrySpec EOF
            {
             newCompositeNode(grammarAccess.getRegistrySpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRegistrySpec=ruleRegistrySpec();

            state._fsp--;

             current =iv_ruleRegistrySpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRegistrySpec"


    // $ANTLR start "ruleRegistrySpec"
    // InternalMlopsDsl.g:2890:1: ruleRegistrySpec returns [EObject current=null] : (otherlv_0= 'RegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) (otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) ) )? (otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']' )? (otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) ) )? (otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) ) )? otherlv_22= '}' ) ;
    public final EObject ruleRegistrySpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_kind_5_0=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_registerAs_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token lv_tags_12_0=null;
        Token otherlv_13=null;
        Token lv_tags_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token lv_description_18_0=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Enumerator lv_stage_21_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:2896:2: ( (otherlv_0= 'RegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) (otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) ) )? (otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']' )? (otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) ) )? (otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) ) )? otherlv_22= '}' ) )
            // InternalMlopsDsl.g:2897:2: (otherlv_0= 'RegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) (otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) ) )? (otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']' )? (otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) ) )? (otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) ) )? otherlv_22= '}' )
            {
            // InternalMlopsDsl.g:2897:2: (otherlv_0= 'RegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) (otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) ) )? (otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']' )? (otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) ) )? (otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) ) )? otherlv_22= '}' )
            // InternalMlopsDsl.g:2898:3: otherlv_0= 'RegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) (otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) ) )? (otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']' )? (otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) ) )? (otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) ) )? otherlv_22= '}'
            {
            otherlv_0=(Token)match(input,95,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getRegistrySpecAccess().getRegistrySpecKeyword_0());
            		
            // InternalMlopsDsl.g:2902:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:2903:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:2903:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:2904:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getRegistrySpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRegistrySpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getRegistrySpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getRegistrySpecAccess().getKindKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_4, grammarAccess.getRegistrySpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:2932:3: ( (lv_kind_5_0= RULE_STRING ) )
            // InternalMlopsDsl.g:2933:4: (lv_kind_5_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:2933:4: (lv_kind_5_0= RULE_STRING )
            // InternalMlopsDsl.g:2934:5: lv_kind_5_0= RULE_STRING
            {
            lv_kind_5_0=(Token)match(input,RULE_STRING,FOLLOW_88); 

            					newLeafNode(lv_kind_5_0, grammarAccess.getRegistrySpecAccess().getKindSTRINGTerminalRuleCall_5_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRegistrySpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"kind",
            						lv_kind_5_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:2950:3: (otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) ) )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==96) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // InternalMlopsDsl.g:2951:4: otherlv_6= 'registerAs' otherlv_7= '=' ( (lv_registerAs_8_0= RULE_STRING ) )
                    {
                    otherlv_6=(Token)match(input,96,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getRegistrySpecAccess().getRegisterAsKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_7, grammarAccess.getRegistrySpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:2959:4: ( (lv_registerAs_8_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:2960:5: (lv_registerAs_8_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:2960:5: (lv_registerAs_8_0= RULE_STRING )
                    // InternalMlopsDsl.g:2961:6: lv_registerAs_8_0= RULE_STRING
                    {
                    lv_registerAs_8_0=(Token)match(input,RULE_STRING,FOLLOW_89); 

                    						newLeafNode(lv_registerAs_8_0, grammarAccess.getRegistrySpecAccess().getRegisterAsSTRINGTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRegistrySpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"registerAs",
                    							lv_registerAs_8_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:2978:3: (otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']' )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==97) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // InternalMlopsDsl.g:2979:4: otherlv_9= 'tags' otherlv_10= '=' otherlv_11= '[' ( (lv_tags_12_0= RULE_STRING ) ) (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )* otherlv_15= ']'
                    {
                    otherlv_9=(Token)match(input,97,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getRegistrySpecAccess().getTagsKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_10, grammarAccess.getRegistrySpecAccess().getEqualsSignKeyword_7_1());
                    			
                    otherlv_11=(Token)match(input,35,FOLLOW_7); 

                    				newLeafNode(otherlv_11, grammarAccess.getRegistrySpecAccess().getLeftSquareBracketKeyword_7_2());
                    			
                    // InternalMlopsDsl.g:2991:4: ( (lv_tags_12_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:2992:5: (lv_tags_12_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:2992:5: (lv_tags_12_0= RULE_STRING )
                    // InternalMlopsDsl.g:2993:6: lv_tags_12_0= RULE_STRING
                    {
                    lv_tags_12_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    						newLeafNode(lv_tags_12_0, grammarAccess.getRegistrySpecAccess().getTagsSTRINGTerminalRuleCall_7_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRegistrySpecRule());
                    						}
                    						addWithLastConsumed(
                    							current,
                    							"tags",
                    							lv_tags_12_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3009:4: (otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) ) )*
                    loop39:
                    do {
                        int alt39=2;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0==36) ) {
                            alt39=1;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3010:5: otherlv_13= ',' ( (lv_tags_14_0= RULE_STRING ) )
                    	    {
                    	    otherlv_13=(Token)match(input,36,FOLLOW_7); 

                    	    					newLeafNode(otherlv_13, grammarAccess.getRegistrySpecAccess().getCommaKeyword_7_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3014:5: ( (lv_tags_14_0= RULE_STRING ) )
                    	    // InternalMlopsDsl.g:3015:6: (lv_tags_14_0= RULE_STRING )
                    	    {
                    	    // InternalMlopsDsl.g:3015:6: (lv_tags_14_0= RULE_STRING )
                    	    // InternalMlopsDsl.g:3016:7: lv_tags_14_0= RULE_STRING
                    	    {
                    	    lv_tags_14_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    	    							newLeafNode(lv_tags_14_0, grammarAccess.getRegistrySpecAccess().getTagsSTRINGTerminalRuleCall_7_4_1_0());
                    	    						

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getRegistrySpecRule());
                    	    							}
                    	    							addWithLastConsumed(
                    	    								current,
                    	    								"tags",
                    	    								lv_tags_14_0,
                    	    								"org.eclipse.xtext.common.Terminals.STRING");
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);

                    otherlv_15=(Token)match(input,37,FOLLOW_90); 

                    				newLeafNode(otherlv_15, grammarAccess.getRegistrySpecAccess().getRightSquareBracketKeyword_7_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:3038:3: (otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) ) )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==98) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // InternalMlopsDsl.g:3039:4: otherlv_16= 'description' otherlv_17= '=' ( (lv_description_18_0= RULE_STRING ) )
                    {
                    otherlv_16=(Token)match(input,98,FOLLOW_6); 

                    				newLeafNode(otherlv_16, grammarAccess.getRegistrySpecAccess().getDescriptionKeyword_8_0());
                    			
                    otherlv_17=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_17, grammarAccess.getRegistrySpecAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:3047:4: ( (lv_description_18_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:3048:5: (lv_description_18_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:3048:5: (lv_description_18_0= RULE_STRING )
                    // InternalMlopsDsl.g:3049:6: lv_description_18_0= RULE_STRING
                    {
                    lv_description_18_0=(Token)match(input,RULE_STRING,FOLLOW_91); 

                    						newLeafNode(lv_description_18_0, grammarAccess.getRegistrySpecAccess().getDescriptionSTRINGTerminalRuleCall_8_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRegistrySpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"description",
                    							lv_description_18_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3066:3: (otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) ) )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==99) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // InternalMlopsDsl.g:3067:4: otherlv_19= 'stage' otherlv_20= '=' ( (lv_stage_21_0= ruleRegistryStage ) )
                    {
                    otherlv_19=(Token)match(input,99,FOLLOW_6); 

                    				newLeafNode(otherlv_19, grammarAccess.getRegistrySpecAccess().getStageKeyword_9_0());
                    			
                    otherlv_20=(Token)match(input,15,FOLLOW_92); 

                    				newLeafNode(otherlv_20, grammarAccess.getRegistrySpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:3075:4: ( (lv_stage_21_0= ruleRegistryStage ) )
                    // InternalMlopsDsl.g:3076:5: (lv_stage_21_0= ruleRegistryStage )
                    {
                    // InternalMlopsDsl.g:3076:5: (lv_stage_21_0= ruleRegistryStage )
                    // InternalMlopsDsl.g:3077:6: lv_stage_21_0= ruleRegistryStage
                    {

                    						newCompositeNode(grammarAccess.getRegistrySpecAccess().getStageRegistryStageEnumRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_stage_21_0=ruleRegistryStage();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRegistrySpecRule());
                    						}
                    						set(
                    							current,
                    							"stage",
                    							lv_stage_21_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.RegistryStage");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_22=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_22, grammarAccess.getRegistrySpecAccess().getRightCurlyBracketKeyword_10());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegistrySpec"


    // $ANTLR start "entryRuleServingSpec"
    // InternalMlopsDsl.g:3103:1: entryRuleServingSpec returns [EObject current=null] : iv_ruleServingSpec= ruleServingSpec EOF ;
    public final EObject entryRuleServingSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleServingSpec = null;


        try {
            // InternalMlopsDsl.g:3103:52: (iv_ruleServingSpec= ruleServingSpec EOF )
            // InternalMlopsDsl.g:3104:2: iv_ruleServingSpec= ruleServingSpec EOF
            {
             newCompositeNode(grammarAccess.getServingSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleServingSpec=ruleServingSpec();

            state._fsp--;

             current =iv_ruleServingSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleServingSpec"


    // $ANTLR start "ruleServingSpec"
    // InternalMlopsDsl.g:3110:1: ruleServingSpec returns [EObject current=null] : (otherlv_0= 'ServingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= ruleServingKind ) ) (otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) ) )? (otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) ) )? (otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) ) )? (otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) ) )? (otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) ) )? (otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) ) )? (otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']' )? (otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']' )? (otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']' )? otherlv_45= '}' ) ;
    public final EObject ruleServingSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_port_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_healthPath_11_0=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token lv_readinessPath_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_replicas_17_0=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_autoscaling_20_1=null;
        Token lv_autoscaling_20_2=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token lv_timeoutSeconds_23_0=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_32=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_38=null;
        Token otherlv_39=null;
        Token otherlv_40=null;
        Token lv_routes_41_0=null;
        Token otherlv_42=null;
        Token lv_routes_43_0=null;
        Token otherlv_44=null;
        Token otherlv_45=null;
        Enumerator lv_kind_5_0 = null;

        EObject lv_resources_27_0 = null;

        EObject lv_resources_29_0 = null;

        EObject lv_env_34_0 = null;

        EObject lv_env_36_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:3116:2: ( (otherlv_0= 'ServingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= ruleServingKind ) ) (otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) ) )? (otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) ) )? (otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) ) )? (otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) ) )? (otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) ) )? (otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) ) )? (otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']' )? (otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']' )? (otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']' )? otherlv_45= '}' ) )
            // InternalMlopsDsl.g:3117:2: (otherlv_0= 'ServingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= ruleServingKind ) ) (otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) ) )? (otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) ) )? (otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) ) )? (otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) ) )? (otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) ) )? (otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) ) )? (otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']' )? (otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']' )? (otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']' )? otherlv_45= '}' )
            {
            // InternalMlopsDsl.g:3117:2: (otherlv_0= 'ServingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= ruleServingKind ) ) (otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) ) )? (otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) ) )? (otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) ) )? (otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) ) )? (otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) ) )? (otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) ) )? (otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']' )? (otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']' )? (otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']' )? otherlv_45= '}' )
            // InternalMlopsDsl.g:3118:3: otherlv_0= 'ServingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= ruleServingKind ) ) (otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) ) )? (otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) ) )? (otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) ) )? (otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) ) )? (otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) ) )? (otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) ) )? (otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']' )? (otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']' )? (otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']' )? otherlv_45= '}'
            {
            otherlv_0=(Token)match(input,100,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getServingSpecAccess().getServingSpecKeyword_0());
            		
            // InternalMlopsDsl.g:3122:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:3123:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:3123:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:3124:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getServingSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getServingSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getServingSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getServingSpecAccess().getKindKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_93); 

            			newLeafNode(otherlv_4, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:3152:3: ( (lv_kind_5_0= ruleServingKind ) )
            // InternalMlopsDsl.g:3153:4: (lv_kind_5_0= ruleServingKind )
            {
            // InternalMlopsDsl.g:3153:4: (lv_kind_5_0= ruleServingKind )
            // InternalMlopsDsl.g:3154:5: lv_kind_5_0= ruleServingKind
            {

            					newCompositeNode(grammarAccess.getServingSpecAccess().getKindServingKindEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_94);
            lv_kind_5_0=ruleServingKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getServingSpecRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ServingKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:3171:3: (otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) ) )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==101) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // InternalMlopsDsl.g:3172:4: otherlv_6= 'port' otherlv_7= '=' ( (lv_port_8_0= RULE_INT ) )
                    {
                    otherlv_6=(Token)match(input,101,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getServingSpecAccess().getPortKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_17); 

                    				newLeafNode(otherlv_7, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:3180:4: ( (lv_port_8_0= RULE_INT ) )
                    // InternalMlopsDsl.g:3181:5: (lv_port_8_0= RULE_INT )
                    {
                    // InternalMlopsDsl.g:3181:5: (lv_port_8_0= RULE_INT )
                    // InternalMlopsDsl.g:3182:6: lv_port_8_0= RULE_INT
                    {
                    lv_port_8_0=(Token)match(input,RULE_INT,FOLLOW_95); 

                    						newLeafNode(lv_port_8_0, grammarAccess.getServingSpecAccess().getPortINTTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getServingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"port",
                    							lv_port_8_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3199:3: (otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) ) )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==102) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // InternalMlopsDsl.g:3200:4: otherlv_9= 'healthPath' otherlv_10= '=' ( (lv_healthPath_11_0= RULE_STRING ) )
                    {
                    otherlv_9=(Token)match(input,102,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getServingSpecAccess().getHealthPathKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_10, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:3208:4: ( (lv_healthPath_11_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:3209:5: (lv_healthPath_11_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:3209:5: (lv_healthPath_11_0= RULE_STRING )
                    // InternalMlopsDsl.g:3210:6: lv_healthPath_11_0= RULE_STRING
                    {
                    lv_healthPath_11_0=(Token)match(input,RULE_STRING,FOLLOW_96); 

                    						newLeafNode(lv_healthPath_11_0, grammarAccess.getServingSpecAccess().getHealthPathSTRINGTerminalRuleCall_7_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getServingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"healthPath",
                    							lv_healthPath_11_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3227:3: (otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) ) )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==103) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // InternalMlopsDsl.g:3228:4: otherlv_12= 'readinessPath' otherlv_13= '=' ( (lv_readinessPath_14_0= RULE_STRING ) )
                    {
                    otherlv_12=(Token)match(input,103,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getServingSpecAccess().getReadinessPathKeyword_8_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_13, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:3236:4: ( (lv_readinessPath_14_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:3237:5: (lv_readinessPath_14_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:3237:5: (lv_readinessPath_14_0= RULE_STRING )
                    // InternalMlopsDsl.g:3238:6: lv_readinessPath_14_0= RULE_STRING
                    {
                    lv_readinessPath_14_0=(Token)match(input,RULE_STRING,FOLLOW_97); 

                    						newLeafNode(lv_readinessPath_14_0, grammarAccess.getServingSpecAccess().getReadinessPathSTRINGTerminalRuleCall_8_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getServingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"readinessPath",
                    							lv_readinessPath_14_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3255:3: (otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) ) )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==104) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // InternalMlopsDsl.g:3256:4: otherlv_15= 'replicas' otherlv_16= '=' ( (lv_replicas_17_0= RULE_INT ) )
                    {
                    otherlv_15=(Token)match(input,104,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getServingSpecAccess().getReplicasKeyword_9_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_17); 

                    				newLeafNode(otherlv_16, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:3264:4: ( (lv_replicas_17_0= RULE_INT ) )
                    // InternalMlopsDsl.g:3265:5: (lv_replicas_17_0= RULE_INT )
                    {
                    // InternalMlopsDsl.g:3265:5: (lv_replicas_17_0= RULE_INT )
                    // InternalMlopsDsl.g:3266:6: lv_replicas_17_0= RULE_INT
                    {
                    lv_replicas_17_0=(Token)match(input,RULE_INT,FOLLOW_98); 

                    						newLeafNode(lv_replicas_17_0, grammarAccess.getServingSpecAccess().getReplicasINTTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getServingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"replicas",
                    							lv_replicas_17_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3283:3: (otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==105) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // InternalMlopsDsl.g:3284:4: otherlv_18= 'autoscaling' otherlv_19= '=' ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) )
                    {
                    otherlv_18=(Token)match(input,105,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getServingSpecAccess().getAutoscalingKeyword_10_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_19, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:3292:4: ( ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) ) )
                    // InternalMlopsDsl.g:3293:5: ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:3293:5: ( (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' ) )
                    // InternalMlopsDsl.g:3294:6: (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' )
                    {
                    // InternalMlopsDsl.g:3294:6: (lv_autoscaling_20_1= 'true' | lv_autoscaling_20_2= 'false' )
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==31) ) {
                        alt47=1;
                    }
                    else if ( (LA47_0==32) ) {
                        alt47=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 47, 0, input);

                        throw nvae;
                    }
                    switch (alt47) {
                        case 1 :
                            // InternalMlopsDsl.g:3295:7: lv_autoscaling_20_1= 'true'
                            {
                            lv_autoscaling_20_1=(Token)match(input,31,FOLLOW_99); 

                            							newLeafNode(lv_autoscaling_20_1, grammarAccess.getServingSpecAccess().getAutoscalingTrueKeyword_10_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getServingSpecRule());
                            							}
                            							setWithLastConsumed(current, "autoscaling", lv_autoscaling_20_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:3306:7: lv_autoscaling_20_2= 'false'
                            {
                            lv_autoscaling_20_2=(Token)match(input,32,FOLLOW_99); 

                            							newLeafNode(lv_autoscaling_20_2, grammarAccess.getServingSpecAccess().getAutoscalingFalseKeyword_10_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getServingSpecRule());
                            							}
                            							setWithLastConsumed(current, "autoscaling", lv_autoscaling_20_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3320:3: (otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==106) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // InternalMlopsDsl.g:3321:4: otherlv_21= 'timeoutSeconds' otherlv_22= '=' ( (lv_timeoutSeconds_23_0= RULE_INT ) )
                    {
                    otherlv_21=(Token)match(input,106,FOLLOW_6); 

                    				newLeafNode(otherlv_21, grammarAccess.getServingSpecAccess().getTimeoutSecondsKeyword_11_0());
                    			
                    otherlv_22=(Token)match(input,15,FOLLOW_17); 

                    				newLeafNode(otherlv_22, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:3329:4: ( (lv_timeoutSeconds_23_0= RULE_INT ) )
                    // InternalMlopsDsl.g:3330:5: (lv_timeoutSeconds_23_0= RULE_INT )
                    {
                    // InternalMlopsDsl.g:3330:5: (lv_timeoutSeconds_23_0= RULE_INT )
                    // InternalMlopsDsl.g:3331:6: lv_timeoutSeconds_23_0= RULE_INT
                    {
                    lv_timeoutSeconds_23_0=(Token)match(input,RULE_INT,FOLLOW_100); 

                    						newLeafNode(lv_timeoutSeconds_23_0, grammarAccess.getServingSpecAccess().getTimeoutSecondsINTTerminalRuleCall_11_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getServingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"timeoutSeconds",
                    							lv_timeoutSeconds_23_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3348:3: (otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']' )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==87) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // InternalMlopsDsl.g:3349:4: otherlv_24= 'resources' otherlv_25= '=' otherlv_26= '[' ( (lv_resources_27_0= ruleResource ) ) (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )* otherlv_30= ']'
                    {
                    otherlv_24=(Token)match(input,87,FOLLOW_6); 

                    				newLeafNode(otherlv_24, grammarAccess.getServingSpecAccess().getResourcesKeyword_12_0());
                    			
                    otherlv_25=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_25, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_12_1());
                    			
                    otherlv_26=(Token)match(input,35,FOLLOW_80); 

                    				newLeafNode(otherlv_26, grammarAccess.getServingSpecAccess().getLeftSquareBracketKeyword_12_2());
                    			
                    // InternalMlopsDsl.g:3361:4: ( (lv_resources_27_0= ruleResource ) )
                    // InternalMlopsDsl.g:3362:5: (lv_resources_27_0= ruleResource )
                    {
                    // InternalMlopsDsl.g:3362:5: (lv_resources_27_0= ruleResource )
                    // InternalMlopsDsl.g:3363:6: lv_resources_27_0= ruleResource
                    {

                    						newCompositeNode(grammarAccess.getServingSpecAccess().getResourcesResourceParserRuleCall_12_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_resources_27_0=ruleResource();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getServingSpecRule());
                    						}
                    						add(
                    							current,
                    							"resources",
                    							lv_resources_27_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Resource");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3380:4: (otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) ) )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==36) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3381:5: otherlv_28= ',' ( (lv_resources_29_0= ruleResource ) )
                    	    {
                    	    otherlv_28=(Token)match(input,36,FOLLOW_80); 

                    	    					newLeafNode(otherlv_28, grammarAccess.getServingSpecAccess().getCommaKeyword_12_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3385:5: ( (lv_resources_29_0= ruleResource ) )
                    	    // InternalMlopsDsl.g:3386:6: (lv_resources_29_0= ruleResource )
                    	    {
                    	    // InternalMlopsDsl.g:3386:6: (lv_resources_29_0= ruleResource )
                    	    // InternalMlopsDsl.g:3387:7: lv_resources_29_0= ruleResource
                    	    {

                    	    							newCompositeNode(grammarAccess.getServingSpecAccess().getResourcesResourceParserRuleCall_12_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_resources_29_0=ruleResource();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getServingSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"resources",
                    	    								lv_resources_29_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Resource");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);

                    otherlv_30=(Token)match(input,37,FOLLOW_101); 

                    				newLeafNode(otherlv_30, grammarAccess.getServingSpecAccess().getRightSquareBracketKeyword_12_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:3410:3: (otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']' )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==88) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // InternalMlopsDsl.g:3411:4: otherlv_31= 'env' otherlv_32= '=' otherlv_33= '[' ( (lv_env_34_0= ruleParam ) ) (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )* otherlv_37= ']'
                    {
                    otherlv_31=(Token)match(input,88,FOLLOW_6); 

                    				newLeafNode(otherlv_31, grammarAccess.getServingSpecAccess().getEnvKeyword_13_0());
                    			
                    otherlv_32=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_32, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_13_1());
                    			
                    otherlv_33=(Token)match(input,35,FOLLOW_76); 

                    				newLeafNode(otherlv_33, grammarAccess.getServingSpecAccess().getLeftSquareBracketKeyword_13_2());
                    			
                    // InternalMlopsDsl.g:3423:4: ( (lv_env_34_0= ruleParam ) )
                    // InternalMlopsDsl.g:3424:5: (lv_env_34_0= ruleParam )
                    {
                    // InternalMlopsDsl.g:3424:5: (lv_env_34_0= ruleParam )
                    // InternalMlopsDsl.g:3425:6: lv_env_34_0= ruleParam
                    {

                    						newCompositeNode(grammarAccess.getServingSpecAccess().getEnvParamParserRuleCall_13_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_env_34_0=ruleParam();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getServingSpecRule());
                    						}
                    						add(
                    							current,
                    							"env",
                    							lv_env_34_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3442:4: (otherlv_35= ',' ( (lv_env_36_0= ruleParam ) ) )*
                    loop52:
                    do {
                        int alt52=2;
                        int LA52_0 = input.LA(1);

                        if ( (LA52_0==36) ) {
                            alt52=1;
                        }


                        switch (alt52) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3443:5: otherlv_35= ',' ( (lv_env_36_0= ruleParam ) )
                    	    {
                    	    otherlv_35=(Token)match(input,36,FOLLOW_76); 

                    	    					newLeafNode(otherlv_35, grammarAccess.getServingSpecAccess().getCommaKeyword_13_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3447:5: ( (lv_env_36_0= ruleParam ) )
                    	    // InternalMlopsDsl.g:3448:6: (lv_env_36_0= ruleParam )
                    	    {
                    	    // InternalMlopsDsl.g:3448:6: (lv_env_36_0= ruleParam )
                    	    // InternalMlopsDsl.g:3449:7: lv_env_36_0= ruleParam
                    	    {

                    	    							newCompositeNode(grammarAccess.getServingSpecAccess().getEnvParamParserRuleCall_13_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_env_36_0=ruleParam();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getServingSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"env",
                    	    								lv_env_36_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop52;
                        }
                    } while (true);

                    otherlv_37=(Token)match(input,37,FOLLOW_102); 

                    				newLeafNode(otherlv_37, grammarAccess.getServingSpecAccess().getRightSquareBracketKeyword_13_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:3472:3: (otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']' )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==107) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // InternalMlopsDsl.g:3473:4: otherlv_38= 'routes' otherlv_39= '=' otherlv_40= '[' ( (lv_routes_41_0= RULE_STRING ) ) (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )* otherlv_44= ']'
                    {
                    otherlv_38=(Token)match(input,107,FOLLOW_6); 

                    				newLeafNode(otherlv_38, grammarAccess.getServingSpecAccess().getRoutesKeyword_14_0());
                    			
                    otherlv_39=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_39, grammarAccess.getServingSpecAccess().getEqualsSignKeyword_14_1());
                    			
                    otherlv_40=(Token)match(input,35,FOLLOW_7); 

                    				newLeafNode(otherlv_40, grammarAccess.getServingSpecAccess().getLeftSquareBracketKeyword_14_2());
                    			
                    // InternalMlopsDsl.g:3485:4: ( (lv_routes_41_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:3486:5: (lv_routes_41_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:3486:5: (lv_routes_41_0= RULE_STRING )
                    // InternalMlopsDsl.g:3487:6: lv_routes_41_0= RULE_STRING
                    {
                    lv_routes_41_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    						newLeafNode(lv_routes_41_0, grammarAccess.getServingSpecAccess().getRoutesSTRINGTerminalRuleCall_14_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getServingSpecRule());
                    						}
                    						addWithLastConsumed(
                    							current,
                    							"routes",
                    							lv_routes_41_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3503:4: (otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) ) )*
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==36) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3504:5: otherlv_42= ',' ( (lv_routes_43_0= RULE_STRING ) )
                    	    {
                    	    otherlv_42=(Token)match(input,36,FOLLOW_7); 

                    	    					newLeafNode(otherlv_42, grammarAccess.getServingSpecAccess().getCommaKeyword_14_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3508:5: ( (lv_routes_43_0= RULE_STRING ) )
                    	    // InternalMlopsDsl.g:3509:6: (lv_routes_43_0= RULE_STRING )
                    	    {
                    	    // InternalMlopsDsl.g:3509:6: (lv_routes_43_0= RULE_STRING )
                    	    // InternalMlopsDsl.g:3510:7: lv_routes_43_0= RULE_STRING
                    	    {
                    	    lv_routes_43_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    	    							newLeafNode(lv_routes_43_0, grammarAccess.getServingSpecAccess().getRoutesSTRINGTerminalRuleCall_14_4_1_0());
                    	    						

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getServingSpecRule());
                    	    							}
                    	    							addWithLastConsumed(
                    	    								current,
                    	    								"routes",
                    	    								lv_routes_43_0,
                    	    								"org.eclipse.xtext.common.Terminals.STRING");
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);

                    otherlv_44=(Token)match(input,37,FOLLOW_8); 

                    				newLeafNode(otherlv_44, grammarAccess.getServingSpecAccess().getRightSquareBracketKeyword_14_5());
                    			

                    }
                    break;

            }

            otherlv_45=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_45, grammarAccess.getServingSpecAccess().getRightCurlyBracketKeyword_15());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleServingSpec"


    // $ANTLR start "entryRuleDriftMetric"
    // InternalMlopsDsl.g:3540:1: entryRuleDriftMetric returns [EObject current=null] : iv_ruleDriftMetric= ruleDriftMetric EOF ;
    public final EObject entryRuleDriftMetric() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDriftMetric = null;


        try {
            // InternalMlopsDsl.g:3540:52: (iv_ruleDriftMetric= ruleDriftMetric EOF )
            // InternalMlopsDsl.g:3541:2: iv_ruleDriftMetric= ruleDriftMetric EOF
            {
             newCompositeNode(grammarAccess.getDriftMetricRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDriftMetric=ruleDriftMetric();

            state._fsp--;

             current =iv_ruleDriftMetric; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDriftMetric"


    // $ANTLR start "ruleDriftMetric"
    // InternalMlopsDsl.g:3547:1: ruleDriftMetric returns [EObject current=null] : (otherlv_0= 'DriftMetric' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) (otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) ) )? (otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) ) )? otherlv_11= '}' ) ;
    public final EObject ruleDriftMetric() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_warn_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_fail_10_0=null;
        Token otherlv_11=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:3553:2: ( (otherlv_0= 'DriftMetric' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) (otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) ) )? (otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) ) )? otherlv_11= '}' ) )
            // InternalMlopsDsl.g:3554:2: (otherlv_0= 'DriftMetric' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) (otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) ) )? (otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) ) )? otherlv_11= '}' )
            {
            // InternalMlopsDsl.g:3554:2: (otherlv_0= 'DriftMetric' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) (otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) ) )? (otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) ) )? otherlv_11= '}' )
            // InternalMlopsDsl.g:3555:3: otherlv_0= 'DriftMetric' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) (otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) ) )? (otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) ) )? otherlv_11= '}'
            {
            otherlv_0=(Token)match(input,108,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getDriftMetricAccess().getDriftMetricKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getDriftMetricAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getDriftMetricAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getDriftMetricAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:3571:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:3572:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:3572:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:3573:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_103); 

            					newLeafNode(lv_name_4_0, grammarAccess.getDriftMetricAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDriftMetricRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:3589:3: (otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==109) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // InternalMlopsDsl.g:3590:4: otherlv_5= 'warn' otherlv_6= '=' ( (lv_warn_7_0= RULE_FLOAT ) )
                    {
                    otherlv_5=(Token)match(input,109,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getDriftMetricAccess().getWarnKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_6, grammarAccess.getDriftMetricAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:3598:4: ( (lv_warn_7_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:3599:5: (lv_warn_7_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:3599:5: (lv_warn_7_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:3600:6: lv_warn_7_0= RULE_FLOAT
                    {
                    lv_warn_7_0=(Token)match(input,RULE_FLOAT,FOLLOW_104); 

                    						newLeafNode(lv_warn_7_0, grammarAccess.getDriftMetricAccess().getWarnFLOATTerminalRuleCall_5_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDriftMetricRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"warn",
                    							lv_warn_7_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:3617:3: (otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==110) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // InternalMlopsDsl.g:3618:4: otherlv_8= 'fail' otherlv_9= '=' ( (lv_fail_10_0= RULE_FLOAT ) )
                    {
                    otherlv_8=(Token)match(input,110,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getDriftMetricAccess().getFailKeyword_6_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_9, grammarAccess.getDriftMetricAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:3626:4: ( (lv_fail_10_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:3627:5: (lv_fail_10_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:3627:5: (lv_fail_10_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:3628:6: lv_fail_10_0= RULE_FLOAT
                    {
                    lv_fail_10_0=(Token)match(input,RULE_FLOAT,FOLLOW_8); 

                    						newLeafNode(lv_fail_10_0, grammarAccess.getDriftMetricAccess().getFailFLOATTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDriftMetricRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"fail",
                    							lv_fail_10_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_11=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_11, grammarAccess.getDriftMetricAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDriftMetric"


    // $ANTLR start "entryRuleMonitoringSpec"
    // InternalMlopsDsl.g:3653:1: entryRuleMonitoringSpec returns [EObject current=null] : iv_ruleMonitoringSpec= ruleMonitoringSpec EOF ;
    public final EObject entryRuleMonitoringSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMonitoringSpec = null;


        try {
            // InternalMlopsDsl.g:3653:55: (iv_ruleMonitoringSpec= ruleMonitoringSpec EOF )
            // InternalMlopsDsl.g:3654:2: iv_ruleMonitoringSpec= ruleMonitoringSpec EOF
            {
             newCompositeNode(grammarAccess.getMonitoringSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMonitoringSpec=ruleMonitoringSpec();

            state._fsp--;

             current =iv_ruleMonitoringSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMonitoringSpec"


    // $ANTLR start "ruleMonitoringSpec"
    // InternalMlopsDsl.g:3660:1: ruleMonitoringSpec returns [EObject current=null] : (otherlv_0= 'MonitoringSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' )? (otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']' )? (otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']' )? (otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) ) )? otherlv_27= '}' ) ;
    public final EObject ruleMonitoringSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token lv_perfMetrics_6_0=null;
        Token otherlv_7=null;
        Token lv_perfMetrics_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token lv_alertChannel_26_0=null;
        Token otherlv_27=null;
        EObject lv_dataDrift_13_0 = null;

        EObject lv_dataDrift_15_0 = null;

        EObject lv_modelDrift_20_0 = null;

        EObject lv_modelDrift_22_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:3666:2: ( (otherlv_0= 'MonitoringSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' )? (otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']' )? (otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']' )? (otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) ) )? otherlv_27= '}' ) )
            // InternalMlopsDsl.g:3667:2: (otherlv_0= 'MonitoringSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' )? (otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']' )? (otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']' )? (otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) ) )? otherlv_27= '}' )
            {
            // InternalMlopsDsl.g:3667:2: (otherlv_0= 'MonitoringSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' )? (otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']' )? (otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']' )? (otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) ) )? otherlv_27= '}' )
            // InternalMlopsDsl.g:3668:3: otherlv_0= 'MonitoringSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' )? (otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']' )? (otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']' )? (otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) ) )? otherlv_27= '}'
            {
            otherlv_0=(Token)match(input,111,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getMonitoringSpecAccess().getMonitoringSpecKeyword_0());
            		
            // InternalMlopsDsl.g:3672:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:3673:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:3673:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:3674:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getMonitoringSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMonitoringSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_105); 

            			newLeafNode(otherlv_2, grammarAccess.getMonitoringSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMlopsDsl.g:3694:3: (otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']' )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==112) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // InternalMlopsDsl.g:3695:4: otherlv_3= 'perfMetrics' otherlv_4= '=' otherlv_5= '[' ( (lv_perfMetrics_6_0= RULE_STRING ) ) (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )* otherlv_9= ']'
                    {
                    otherlv_3=(Token)match(input,112,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getMonitoringSpecAccess().getPerfMetricsKeyword_3_0());
                    			
                    otherlv_4=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_4, grammarAccess.getMonitoringSpecAccess().getEqualsSignKeyword_3_1());
                    			
                    otherlv_5=(Token)match(input,35,FOLLOW_7); 

                    				newLeafNode(otherlv_5, grammarAccess.getMonitoringSpecAccess().getLeftSquareBracketKeyword_3_2());
                    			
                    // InternalMlopsDsl.g:3707:4: ( (lv_perfMetrics_6_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:3708:5: (lv_perfMetrics_6_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:3708:5: (lv_perfMetrics_6_0= RULE_STRING )
                    // InternalMlopsDsl.g:3709:6: lv_perfMetrics_6_0= RULE_STRING
                    {
                    lv_perfMetrics_6_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    						newLeafNode(lv_perfMetrics_6_0, grammarAccess.getMonitoringSpecAccess().getPerfMetricsSTRINGTerminalRuleCall_3_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMonitoringSpecRule());
                    						}
                    						addWithLastConsumed(
                    							current,
                    							"perfMetrics",
                    							lv_perfMetrics_6_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3725:4: (otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) ) )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==36) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3726:5: otherlv_7= ',' ( (lv_perfMetrics_8_0= RULE_STRING ) )
                    	    {
                    	    otherlv_7=(Token)match(input,36,FOLLOW_7); 

                    	    					newLeafNode(otherlv_7, grammarAccess.getMonitoringSpecAccess().getCommaKeyword_3_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3730:5: ( (lv_perfMetrics_8_0= RULE_STRING ) )
                    	    // InternalMlopsDsl.g:3731:6: (lv_perfMetrics_8_0= RULE_STRING )
                    	    {
                    	    // InternalMlopsDsl.g:3731:6: (lv_perfMetrics_8_0= RULE_STRING )
                    	    // InternalMlopsDsl.g:3732:7: lv_perfMetrics_8_0= RULE_STRING
                    	    {
                    	    lv_perfMetrics_8_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    	    							newLeafNode(lv_perfMetrics_8_0, grammarAccess.getMonitoringSpecAccess().getPerfMetricsSTRINGTerminalRuleCall_3_4_1_0());
                    	    						

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getMonitoringSpecRule());
                    	    							}
                    	    							addWithLastConsumed(
                    	    								current,
                    	    								"perfMetrics",
                    	    								lv_perfMetrics_8_0,
                    	    								"org.eclipse.xtext.common.Terminals.STRING");
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);

                    otherlv_9=(Token)match(input,37,FOLLOW_106); 

                    				newLeafNode(otherlv_9, grammarAccess.getMonitoringSpecAccess().getRightSquareBracketKeyword_3_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:3754:3: (otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']' )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==113) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // InternalMlopsDsl.g:3755:4: otherlv_10= 'dataDrift' otherlv_11= '=' otherlv_12= '[' ( (lv_dataDrift_13_0= ruleDriftMetric ) ) (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )* otherlv_16= ']'
                    {
                    otherlv_10=(Token)match(input,113,FOLLOW_6); 

                    				newLeafNode(otherlv_10, grammarAccess.getMonitoringSpecAccess().getDataDriftKeyword_4_0());
                    			
                    otherlv_11=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_11, grammarAccess.getMonitoringSpecAccess().getEqualsSignKeyword_4_1());
                    			
                    otherlv_12=(Token)match(input,35,FOLLOW_107); 

                    				newLeafNode(otherlv_12, grammarAccess.getMonitoringSpecAccess().getLeftSquareBracketKeyword_4_2());
                    			
                    // InternalMlopsDsl.g:3767:4: ( (lv_dataDrift_13_0= ruleDriftMetric ) )
                    // InternalMlopsDsl.g:3768:5: (lv_dataDrift_13_0= ruleDriftMetric )
                    {
                    // InternalMlopsDsl.g:3768:5: (lv_dataDrift_13_0= ruleDriftMetric )
                    // InternalMlopsDsl.g:3769:6: lv_dataDrift_13_0= ruleDriftMetric
                    {

                    						newCompositeNode(grammarAccess.getMonitoringSpecAccess().getDataDriftDriftMetricParserRuleCall_4_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_dataDrift_13_0=ruleDriftMetric();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMonitoringSpecRule());
                    						}
                    						add(
                    							current,
                    							"dataDrift",
                    							lv_dataDrift_13_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.DriftMetric");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3786:4: (otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) ) )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==36) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3787:5: otherlv_14= ',' ( (lv_dataDrift_15_0= ruleDriftMetric ) )
                    	    {
                    	    otherlv_14=(Token)match(input,36,FOLLOW_107); 

                    	    					newLeafNode(otherlv_14, grammarAccess.getMonitoringSpecAccess().getCommaKeyword_4_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3791:5: ( (lv_dataDrift_15_0= ruleDriftMetric ) )
                    	    // InternalMlopsDsl.g:3792:6: (lv_dataDrift_15_0= ruleDriftMetric )
                    	    {
                    	    // InternalMlopsDsl.g:3792:6: (lv_dataDrift_15_0= ruleDriftMetric )
                    	    // InternalMlopsDsl.g:3793:7: lv_dataDrift_15_0= ruleDriftMetric
                    	    {

                    	    							newCompositeNode(grammarAccess.getMonitoringSpecAccess().getDataDriftDriftMetricParserRuleCall_4_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_dataDrift_15_0=ruleDriftMetric();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getMonitoringSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"dataDrift",
                    	    								lv_dataDrift_15_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.DriftMetric");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);

                    otherlv_16=(Token)match(input,37,FOLLOW_108); 

                    				newLeafNode(otherlv_16, grammarAccess.getMonitoringSpecAccess().getRightSquareBracketKeyword_4_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:3816:3: (otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']' )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==114) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // InternalMlopsDsl.g:3817:4: otherlv_17= 'modelDrift' otherlv_18= '=' otherlv_19= '[' ( (lv_modelDrift_20_0= ruleDriftMetric ) ) (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )* otherlv_23= ']'
                    {
                    otherlv_17=(Token)match(input,114,FOLLOW_6); 

                    				newLeafNode(otherlv_17, grammarAccess.getMonitoringSpecAccess().getModelDriftKeyword_5_0());
                    			
                    otherlv_18=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_18, grammarAccess.getMonitoringSpecAccess().getEqualsSignKeyword_5_1());
                    			
                    otherlv_19=(Token)match(input,35,FOLLOW_107); 

                    				newLeafNode(otherlv_19, grammarAccess.getMonitoringSpecAccess().getLeftSquareBracketKeyword_5_2());
                    			
                    // InternalMlopsDsl.g:3829:4: ( (lv_modelDrift_20_0= ruleDriftMetric ) )
                    // InternalMlopsDsl.g:3830:5: (lv_modelDrift_20_0= ruleDriftMetric )
                    {
                    // InternalMlopsDsl.g:3830:5: (lv_modelDrift_20_0= ruleDriftMetric )
                    // InternalMlopsDsl.g:3831:6: lv_modelDrift_20_0= ruleDriftMetric
                    {

                    						newCompositeNode(grammarAccess.getMonitoringSpecAccess().getModelDriftDriftMetricParserRuleCall_5_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_modelDrift_20_0=ruleDriftMetric();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMonitoringSpecRule());
                    						}
                    						add(
                    							current,
                    							"modelDrift",
                    							lv_modelDrift_20_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.DriftMetric");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:3848:4: (otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) ) )*
                    loop62:
                    do {
                        int alt62=2;
                        int LA62_0 = input.LA(1);

                        if ( (LA62_0==36) ) {
                            alt62=1;
                        }


                        switch (alt62) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:3849:5: otherlv_21= ',' ( (lv_modelDrift_22_0= ruleDriftMetric ) )
                    	    {
                    	    otherlv_21=(Token)match(input,36,FOLLOW_107); 

                    	    					newLeafNode(otherlv_21, grammarAccess.getMonitoringSpecAccess().getCommaKeyword_5_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:3853:5: ( (lv_modelDrift_22_0= ruleDriftMetric ) )
                    	    // InternalMlopsDsl.g:3854:6: (lv_modelDrift_22_0= ruleDriftMetric )
                    	    {
                    	    // InternalMlopsDsl.g:3854:6: (lv_modelDrift_22_0= ruleDriftMetric )
                    	    // InternalMlopsDsl.g:3855:7: lv_modelDrift_22_0= ruleDriftMetric
                    	    {

                    	    							newCompositeNode(grammarAccess.getMonitoringSpecAccess().getModelDriftDriftMetricParserRuleCall_5_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_modelDrift_22_0=ruleDriftMetric();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getMonitoringSpecRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"modelDrift",
                    	    								lv_modelDrift_22_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.DriftMetric");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop62;
                        }
                    } while (true);

                    otherlv_23=(Token)match(input,37,FOLLOW_109); 

                    				newLeafNode(otherlv_23, grammarAccess.getMonitoringSpecAccess().getRightSquareBracketKeyword_5_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:3878:3: (otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) ) )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==115) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // InternalMlopsDsl.g:3879:4: otherlv_24= 'alertChannel' otherlv_25= '=' ( (lv_alertChannel_26_0= RULE_STRING ) )
                    {
                    otherlv_24=(Token)match(input,115,FOLLOW_6); 

                    				newLeafNode(otherlv_24, grammarAccess.getMonitoringSpecAccess().getAlertChannelKeyword_6_0());
                    			
                    otherlv_25=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_25, grammarAccess.getMonitoringSpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:3887:4: ( (lv_alertChannel_26_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:3888:5: (lv_alertChannel_26_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:3888:5: (lv_alertChannel_26_0= RULE_STRING )
                    // InternalMlopsDsl.g:3889:6: lv_alertChannel_26_0= RULE_STRING
                    {
                    lv_alertChannel_26_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_alertChannel_26_0, grammarAccess.getMonitoringSpecAccess().getAlertChannelSTRINGTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getMonitoringSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"alertChannel",
                    							lv_alertChannel_26_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_27=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_27, grammarAccess.getMonitoringSpecAccess().getRightCurlyBracketKeyword_7());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMonitoringSpec"


    // $ANTLR start "entryRuleExperimentTrackingSpec"
    // InternalMlopsDsl.g:3914:1: entryRuleExperimentTrackingSpec returns [EObject current=null] : iv_ruleExperimentTrackingSpec= ruleExperimentTrackingSpec EOF ;
    public final EObject entryRuleExperimentTrackingSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleExperimentTrackingSpec = null;


        try {
            // InternalMlopsDsl.g:3914:63: (iv_ruleExperimentTrackingSpec= ruleExperimentTrackingSpec EOF )
            // InternalMlopsDsl.g:3915:2: iv_ruleExperimentTrackingSpec= ruleExperimentTrackingSpec EOF
            {
             newCompositeNode(grammarAccess.getExperimentTrackingSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleExperimentTrackingSpec=ruleExperimentTrackingSpec();

            state._fsp--;

             current =iv_ruleExperimentTrackingSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleExperimentTrackingSpec"


    // $ANTLR start "ruleExperimentTrackingSpec"
    // InternalMlopsDsl.g:3921:1: ruleExperimentTrackingSpec returns [EObject current=null] : (otherlv_0= 'ExperimentTrackingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleTrackingBackend ) ) otherlv_6= 'experimentName' otherlv_7= '=' ( (lv_experimentName_8_0= RULE_STRING ) ) (otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) ) )? (otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) ) )? (otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) ) )? otherlv_24= '}' ) ;
    public final EObject ruleExperimentTrackingSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_experimentName_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_trackingUri_11_0=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        Enumerator lv_backend_5_0 = null;

        AntlrDatatypeRuleToken lv_logParams_14_0 = null;

        AntlrDatatypeRuleToken lv_logMetrics_17_0 = null;

        AntlrDatatypeRuleToken lv_logArtifacts_20_0 = null;

        AntlrDatatypeRuleToken lv_logModel_23_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:3927:2: ( (otherlv_0= 'ExperimentTrackingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleTrackingBackend ) ) otherlv_6= 'experimentName' otherlv_7= '=' ( (lv_experimentName_8_0= RULE_STRING ) ) (otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) ) )? (otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) ) )? (otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) ) )? otherlv_24= '}' ) )
            // InternalMlopsDsl.g:3928:2: (otherlv_0= 'ExperimentTrackingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleTrackingBackend ) ) otherlv_6= 'experimentName' otherlv_7= '=' ( (lv_experimentName_8_0= RULE_STRING ) ) (otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) ) )? (otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) ) )? (otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) ) )? otherlv_24= '}' )
            {
            // InternalMlopsDsl.g:3928:2: (otherlv_0= 'ExperimentTrackingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleTrackingBackend ) ) otherlv_6= 'experimentName' otherlv_7= '=' ( (lv_experimentName_8_0= RULE_STRING ) ) (otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) ) )? (otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) ) )? (otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) ) )? otherlv_24= '}' )
            // InternalMlopsDsl.g:3929:3: otherlv_0= 'ExperimentTrackingSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleTrackingBackend ) ) otherlv_6= 'experimentName' otherlv_7= '=' ( (lv_experimentName_8_0= RULE_STRING ) ) (otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) ) )? (otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) ) )? (otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) ) )? otherlv_24= '}'
            {
            otherlv_0=(Token)match(input,116,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getExperimentTrackingSpecAccess().getExperimentTrackingSpecKeyword_0());
            		
            // InternalMlopsDsl.g:3933:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:3934:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:3934:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:3935:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getExperimentTrackingSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExperimentTrackingSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_110); 

            			newLeafNode(otherlv_2, grammarAccess.getExperimentTrackingSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,117,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getExperimentTrackingSpecAccess().getBackendKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_111); 

            			newLeafNode(otherlv_4, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:3963:3: ( (lv_backend_5_0= ruleTrackingBackend ) )
            // InternalMlopsDsl.g:3964:4: (lv_backend_5_0= ruleTrackingBackend )
            {
            // InternalMlopsDsl.g:3964:4: (lv_backend_5_0= ruleTrackingBackend )
            // InternalMlopsDsl.g:3965:5: lv_backend_5_0= ruleTrackingBackend
            {

            					newCompositeNode(grammarAccess.getExperimentTrackingSpecAccess().getBackendTrackingBackendEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_112);
            lv_backend_5_0=ruleTrackingBackend();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getExperimentTrackingSpecRule());
            					}
            					set(
            						current,
            						"backend",
            						lv_backend_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.TrackingBackend");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,118,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getExperimentTrackingSpecAccess().getExperimentNameKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_7, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:3990:3: ( (lv_experimentName_8_0= RULE_STRING ) )
            // InternalMlopsDsl.g:3991:4: (lv_experimentName_8_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:3991:4: (lv_experimentName_8_0= RULE_STRING )
            // InternalMlopsDsl.g:3992:5: lv_experimentName_8_0= RULE_STRING
            {
            lv_experimentName_8_0=(Token)match(input,RULE_STRING,FOLLOW_113); 

            					newLeafNode(lv_experimentName_8_0, grammarAccess.getExperimentTrackingSpecAccess().getExperimentNameSTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getExperimentTrackingSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"experimentName",
            						lv_experimentName_8_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:4008:3: (otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) ) )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==119) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // InternalMlopsDsl.g:4009:4: otherlv_9= 'trackingUri' otherlv_10= '=' ( (lv_trackingUri_11_0= RULE_STRING ) )
                    {
                    otherlv_9=(Token)match(input,119,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getExperimentTrackingSpecAccess().getTrackingUriKeyword_9_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_10, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:4017:4: ( (lv_trackingUri_11_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:4018:5: (lv_trackingUri_11_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:4018:5: (lv_trackingUri_11_0= RULE_STRING )
                    // InternalMlopsDsl.g:4019:6: lv_trackingUri_11_0= RULE_STRING
                    {
                    lv_trackingUri_11_0=(Token)match(input,RULE_STRING,FOLLOW_114); 

                    						newLeafNode(lv_trackingUri_11_0, grammarAccess.getExperimentTrackingSpecAccess().getTrackingUriSTRINGTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getExperimentTrackingSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"trackingUri",
                    							lv_trackingUri_11_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4036:3: (otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) ) )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==120) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // InternalMlopsDsl.g:4037:4: otherlv_12= 'logParams' otherlv_13= '=' ( (lv_logParams_14_0= ruleBooleanValue ) )
                    {
                    otherlv_12=(Token)match(input,120,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getExperimentTrackingSpecAccess().getLogParamsKeyword_10_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_13, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:4045:4: ( (lv_logParams_14_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4046:5: (lv_logParams_14_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4046:5: (lv_logParams_14_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4047:6: lv_logParams_14_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getExperimentTrackingSpecAccess().getLogParamsBooleanValueParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_115);
                    lv_logParams_14_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExperimentTrackingSpecRule());
                    						}
                    						set(
                    							current,
                    							"logParams",
                    							lv_logParams_14_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4065:3: (otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==121) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // InternalMlopsDsl.g:4066:4: otherlv_15= 'logMetrics' otherlv_16= '=' ( (lv_logMetrics_17_0= ruleBooleanValue ) )
                    {
                    otherlv_15=(Token)match(input,121,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getExperimentTrackingSpecAccess().getLogMetricsKeyword_11_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_16, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:4074:4: ( (lv_logMetrics_17_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4075:5: (lv_logMetrics_17_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4075:5: (lv_logMetrics_17_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4076:6: lv_logMetrics_17_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getExperimentTrackingSpecAccess().getLogMetricsBooleanValueParserRuleCall_11_2_0());
                    					
                    pushFollow(FOLLOW_116);
                    lv_logMetrics_17_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExperimentTrackingSpecRule());
                    						}
                    						set(
                    							current,
                    							"logMetrics",
                    							lv_logMetrics_17_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4094:3: (otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) ) )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==122) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // InternalMlopsDsl.g:4095:4: otherlv_18= 'logArtifacts' otherlv_19= '=' ( (lv_logArtifacts_20_0= ruleBooleanValue ) )
                    {
                    otherlv_18=(Token)match(input,122,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getExperimentTrackingSpecAccess().getLogArtifactsKeyword_12_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_19, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_12_1());
                    			
                    // InternalMlopsDsl.g:4103:4: ( (lv_logArtifacts_20_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4104:5: (lv_logArtifacts_20_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4104:5: (lv_logArtifacts_20_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4105:6: lv_logArtifacts_20_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getExperimentTrackingSpecAccess().getLogArtifactsBooleanValueParserRuleCall_12_2_0());
                    					
                    pushFollow(FOLLOW_117);
                    lv_logArtifacts_20_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExperimentTrackingSpecRule());
                    						}
                    						set(
                    							current,
                    							"logArtifacts",
                    							lv_logArtifacts_20_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4123:3: (otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) ) )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==123) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // InternalMlopsDsl.g:4124:4: otherlv_21= 'logModel' otherlv_22= '=' ( (lv_logModel_23_0= ruleBooleanValue ) )
                    {
                    otherlv_21=(Token)match(input,123,FOLLOW_6); 

                    				newLeafNode(otherlv_21, grammarAccess.getExperimentTrackingSpecAccess().getLogModelKeyword_13_0());
                    			
                    otherlv_22=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_22, grammarAccess.getExperimentTrackingSpecAccess().getEqualsSignKeyword_13_1());
                    			
                    // InternalMlopsDsl.g:4132:4: ( (lv_logModel_23_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4133:5: (lv_logModel_23_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4133:5: (lv_logModel_23_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4134:6: lv_logModel_23_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getExperimentTrackingSpecAccess().getLogModelBooleanValueParserRuleCall_13_2_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_logModel_23_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getExperimentTrackingSpecRule());
                    						}
                    						set(
                    							current,
                    							"logModel",
                    							lv_logModel_23_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_24=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_24, grammarAccess.getExperimentTrackingSpecAccess().getRightCurlyBracketKeyword_14());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExperimentTrackingSpec"


    // $ANTLR start "entryRuleVersioningSpec"
    // InternalMlopsDsl.g:4160:1: entryRuleVersioningSpec returns [EObject current=null] : iv_ruleVersioningSpec= ruleVersioningSpec EOF ;
    public final EObject entryRuleVersioningSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleVersioningSpec = null;


        try {
            // InternalMlopsDsl.g:4160:55: (iv_ruleVersioningSpec= ruleVersioningSpec EOF )
            // InternalMlopsDsl.g:4161:2: iv_ruleVersioningSpec= ruleVersioningSpec EOF
            {
             newCompositeNode(grammarAccess.getVersioningSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleVersioningSpec=ruleVersioningSpec();

            state._fsp--;

             current =iv_ruleVersioningSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleVersioningSpec"


    // $ANTLR start "ruleVersioningSpec"
    // InternalMlopsDsl.g:4167:1: ruleVersioningSpec returns [EObject current=null] : (otherlv_0= 'VersioningSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleVersioningBackend ) ) (otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) ;
    public final EObject ruleVersioningSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_remoteUri_20_0=null;
        Token otherlv_21=null;
        Enumerator lv_backend_5_0 = null;

        AntlrDatatypeRuleToken lv_versionData_8_0 = null;

        AntlrDatatypeRuleToken lv_versionCode_11_0 = null;

        AntlrDatatypeRuleToken lv_versionModels_14_0 = null;

        AntlrDatatypeRuleToken lv_versionParams_17_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:4173:2: ( (otherlv_0= 'VersioningSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleVersioningBackend ) ) (otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) )
            // InternalMlopsDsl.g:4174:2: (otherlv_0= 'VersioningSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleVersioningBackend ) ) (otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            {
            // InternalMlopsDsl.g:4174:2: (otherlv_0= 'VersioningSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleVersioningBackend ) ) (otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            // InternalMlopsDsl.g:4175:3: otherlv_0= 'VersioningSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleVersioningBackend ) ) (otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) ) )? otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,124,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getVersioningSpecAccess().getVersioningSpecKeyword_0());
            		
            // InternalMlopsDsl.g:4179:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:4180:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:4180:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:4181:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getVersioningSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getVersioningSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_110); 

            			newLeafNode(otherlv_2, grammarAccess.getVersioningSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,117,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getVersioningSpecAccess().getBackendKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_118); 

            			newLeafNode(otherlv_4, grammarAccess.getVersioningSpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:4209:3: ( (lv_backend_5_0= ruleVersioningBackend ) )
            // InternalMlopsDsl.g:4210:4: (lv_backend_5_0= ruleVersioningBackend )
            {
            // InternalMlopsDsl.g:4210:4: (lv_backend_5_0= ruleVersioningBackend )
            // InternalMlopsDsl.g:4211:5: lv_backend_5_0= ruleVersioningBackend
            {

            					newCompositeNode(grammarAccess.getVersioningSpecAccess().getBackendVersioningBackendEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_119);
            lv_backend_5_0=ruleVersioningBackend();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getVersioningSpecRule());
            					}
            					set(
            						current,
            						"backend",
            						lv_backend_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.VersioningBackend");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:4228:3: (otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) ) )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==125) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // InternalMlopsDsl.g:4229:4: otherlv_6= 'versionData' otherlv_7= '=' ( (lv_versionData_8_0= ruleBooleanValue ) )
                    {
                    otherlv_6=(Token)match(input,125,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getVersioningSpecAccess().getVersionDataKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_7, grammarAccess.getVersioningSpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:4237:4: ( (lv_versionData_8_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4238:5: (lv_versionData_8_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4238:5: (lv_versionData_8_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4239:6: lv_versionData_8_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getVersioningSpecAccess().getVersionDataBooleanValueParserRuleCall_6_2_0());
                    					
                    pushFollow(FOLLOW_120);
                    lv_versionData_8_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersioningSpecRule());
                    						}
                    						set(
                    							current,
                    							"versionData",
                    							lv_versionData_8_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4257:3: (otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) ) )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==126) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // InternalMlopsDsl.g:4258:4: otherlv_9= 'versionCode' otherlv_10= '=' ( (lv_versionCode_11_0= ruleBooleanValue ) )
                    {
                    otherlv_9=(Token)match(input,126,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getVersioningSpecAccess().getVersionCodeKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_10, grammarAccess.getVersioningSpecAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:4266:4: ( (lv_versionCode_11_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4267:5: (lv_versionCode_11_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4267:5: (lv_versionCode_11_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4268:6: lv_versionCode_11_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getVersioningSpecAccess().getVersionCodeBooleanValueParserRuleCall_7_2_0());
                    					
                    pushFollow(FOLLOW_121);
                    lv_versionCode_11_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersioningSpecRule());
                    						}
                    						set(
                    							current,
                    							"versionCode",
                    							lv_versionCode_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4286:3: (otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) ) )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==127) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // InternalMlopsDsl.g:4287:4: otherlv_12= 'versionModels' otherlv_13= '=' ( (lv_versionModels_14_0= ruleBooleanValue ) )
                    {
                    otherlv_12=(Token)match(input,127,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getVersioningSpecAccess().getVersionModelsKeyword_8_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_13, grammarAccess.getVersioningSpecAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:4295:4: ( (lv_versionModels_14_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4296:5: (lv_versionModels_14_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4296:5: (lv_versionModels_14_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4297:6: lv_versionModels_14_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getVersioningSpecAccess().getVersionModelsBooleanValueParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_122);
                    lv_versionModels_14_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersioningSpecRule());
                    						}
                    						set(
                    							current,
                    							"versionModels",
                    							lv_versionModels_14_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4315:3: (otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) ) )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==128) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // InternalMlopsDsl.g:4316:4: otherlv_15= 'versionParams' otherlv_16= '=' ( (lv_versionParams_17_0= ruleBooleanValue ) )
                    {
                    otherlv_15=(Token)match(input,128,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getVersioningSpecAccess().getVersionParamsKeyword_9_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_16, grammarAccess.getVersioningSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:4324:4: ( (lv_versionParams_17_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4325:5: (lv_versionParams_17_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4325:5: (lv_versionParams_17_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4326:6: lv_versionParams_17_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getVersioningSpecAccess().getVersionParamsBooleanValueParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_123);
                    lv_versionParams_17_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getVersioningSpecRule());
                    						}
                    						set(
                    							current,
                    							"versionParams",
                    							lv_versionParams_17_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4344:3: (otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) ) )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==129) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // InternalMlopsDsl.g:4345:4: otherlv_18= 'remoteUri' otherlv_19= '=' ( (lv_remoteUri_20_0= RULE_STRING ) )
                    {
                    otherlv_18=(Token)match(input,129,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getVersioningSpecAccess().getRemoteUriKeyword_10_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_19, grammarAccess.getVersioningSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:4353:4: ( (lv_remoteUri_20_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:4354:5: (lv_remoteUri_20_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:4354:5: (lv_remoteUri_20_0= RULE_STRING )
                    // InternalMlopsDsl.g:4355:6: lv_remoteUri_20_0= RULE_STRING
                    {
                    lv_remoteUri_20_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_remoteUri_20_0, grammarAccess.getVersioningSpecAccess().getRemoteUriSTRINGTerminalRuleCall_10_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getVersioningSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"remoteUri",
                    							lv_remoteUri_20_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_21=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getVersioningSpecAccess().getRightCurlyBracketKeyword_11());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersioningSpec"


    // $ANTLR start "entryRuleModelRegistrySpec"
    // InternalMlopsDsl.g:4380:1: entryRuleModelRegistrySpec returns [EObject current=null] : iv_ruleModelRegistrySpec= ruleModelRegistrySpec EOF ;
    public final EObject entryRuleModelRegistrySpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleModelRegistrySpec = null;


        try {
            // InternalMlopsDsl.g:4380:58: (iv_ruleModelRegistrySpec= ruleModelRegistrySpec EOF )
            // InternalMlopsDsl.g:4381:2: iv_ruleModelRegistrySpec= ruleModelRegistrySpec EOF
            {
             newCompositeNode(grammarAccess.getModelRegistrySpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleModelRegistrySpec=ruleModelRegistrySpec();

            state._fsp--;

             current =iv_ruleModelRegistrySpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleModelRegistrySpec"


    // $ANTLR start "ruleModelRegistrySpec"
    // InternalMlopsDsl.g:4387:1: ruleModelRegistrySpec returns [EObject current=null] : (otherlv_0= 'ModelRegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleRegistryBackend ) ) otherlv_6= 'modelName' otherlv_7= '=' ( (lv_modelName_8_0= RULE_STRING ) ) (otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) ;
    public final EObject ruleModelRegistrySpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_modelName_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_approvalMode_20_0=null;
        Token otherlv_21=null;
        Enumerator lv_backend_5_0 = null;

        AntlrDatatypeRuleToken lv_enableStaging_11_0 = null;

        AntlrDatatypeRuleToken lv_enableProduction_14_0 = null;

        AntlrDatatypeRuleToken lv_rollbackEnabled_17_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:4393:2: ( (otherlv_0= 'ModelRegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleRegistryBackend ) ) otherlv_6= 'modelName' otherlv_7= '=' ( (lv_modelName_8_0= RULE_STRING ) ) (otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) )
            // InternalMlopsDsl.g:4394:2: (otherlv_0= 'ModelRegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleRegistryBackend ) ) otherlv_6= 'modelName' otherlv_7= '=' ( (lv_modelName_8_0= RULE_STRING ) ) (otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            {
            // InternalMlopsDsl.g:4394:2: (otherlv_0= 'ModelRegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleRegistryBackend ) ) otherlv_6= 'modelName' otherlv_7= '=' ( (lv_modelName_8_0= RULE_STRING ) ) (otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            // InternalMlopsDsl.g:4395:3: otherlv_0= 'ModelRegistrySpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'backend' otherlv_4= '=' ( (lv_backend_5_0= ruleRegistryBackend ) ) otherlv_6= 'modelName' otherlv_7= '=' ( (lv_modelName_8_0= RULE_STRING ) ) (otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) ) )? otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,130,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getModelRegistrySpecAccess().getModelRegistrySpecKeyword_0());
            		
            // InternalMlopsDsl.g:4399:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:4400:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:4400:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:4401:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getModelRegistrySpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getModelRegistrySpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_110); 

            			newLeafNode(otherlv_2, grammarAccess.getModelRegistrySpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,117,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getModelRegistrySpecAccess().getBackendKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_124); 

            			newLeafNode(otherlv_4, grammarAccess.getModelRegistrySpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:4429:3: ( (lv_backend_5_0= ruleRegistryBackend ) )
            // InternalMlopsDsl.g:4430:4: (lv_backend_5_0= ruleRegistryBackend )
            {
            // InternalMlopsDsl.g:4430:4: (lv_backend_5_0= ruleRegistryBackend )
            // InternalMlopsDsl.g:4431:5: lv_backend_5_0= ruleRegistryBackend
            {

            					newCompositeNode(grammarAccess.getModelRegistrySpecAccess().getBackendRegistryBackendEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_125);
            lv_backend_5_0=ruleRegistryBackend();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getModelRegistrySpecRule());
            					}
            					set(
            						current,
            						"backend",
            						lv_backend_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.RegistryBackend");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,131,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getModelRegistrySpecAccess().getModelNameKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_7, grammarAccess.getModelRegistrySpecAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:4456:3: ( (lv_modelName_8_0= RULE_STRING ) )
            // InternalMlopsDsl.g:4457:4: (lv_modelName_8_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:4457:4: (lv_modelName_8_0= RULE_STRING )
            // InternalMlopsDsl.g:4458:5: lv_modelName_8_0= RULE_STRING
            {
            lv_modelName_8_0=(Token)match(input,RULE_STRING,FOLLOW_126); 

            					newLeafNode(lv_modelName_8_0, grammarAccess.getModelRegistrySpecAccess().getModelNameSTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getModelRegistrySpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"modelName",
            						lv_modelName_8_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:4474:3: (otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) ) )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==132) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // InternalMlopsDsl.g:4475:4: otherlv_9= 'enableStaging' otherlv_10= '=' ( (lv_enableStaging_11_0= ruleBooleanValue ) )
                    {
                    otherlv_9=(Token)match(input,132,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getModelRegistrySpecAccess().getEnableStagingKeyword_9_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_10, grammarAccess.getModelRegistrySpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:4483:4: ( (lv_enableStaging_11_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4484:5: (lv_enableStaging_11_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4484:5: (lv_enableStaging_11_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4485:6: lv_enableStaging_11_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getModelRegistrySpecAccess().getEnableStagingBooleanValueParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_127);
                    lv_enableStaging_11_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getModelRegistrySpecRule());
                    						}
                    						set(
                    							current,
                    							"enableStaging",
                    							lv_enableStaging_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4503:3: (otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) ) )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==133) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // InternalMlopsDsl.g:4504:4: otherlv_12= 'enableProduction' otherlv_13= '=' ( (lv_enableProduction_14_0= ruleBooleanValue ) )
                    {
                    otherlv_12=(Token)match(input,133,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getModelRegistrySpecAccess().getEnableProductionKeyword_10_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_13, grammarAccess.getModelRegistrySpecAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:4512:4: ( (lv_enableProduction_14_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4513:5: (lv_enableProduction_14_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4513:5: (lv_enableProduction_14_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4514:6: lv_enableProduction_14_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getModelRegistrySpecAccess().getEnableProductionBooleanValueParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_128);
                    lv_enableProduction_14_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getModelRegistrySpecRule());
                    						}
                    						set(
                    							current,
                    							"enableProduction",
                    							lv_enableProduction_14_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4532:3: (otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) ) )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==134) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // InternalMlopsDsl.g:4533:4: otherlv_15= 'rollbackEnabled' otherlv_16= '=' ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) )
                    {
                    otherlv_15=(Token)match(input,134,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getModelRegistrySpecAccess().getRollbackEnabledKeyword_11_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_16, grammarAccess.getModelRegistrySpecAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:4541:4: ( (lv_rollbackEnabled_17_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4542:5: (lv_rollbackEnabled_17_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4542:5: (lv_rollbackEnabled_17_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4543:6: lv_rollbackEnabled_17_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getModelRegistrySpecAccess().getRollbackEnabledBooleanValueParserRuleCall_11_2_0());
                    					
                    pushFollow(FOLLOW_129);
                    lv_rollbackEnabled_17_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getModelRegistrySpecRule());
                    						}
                    						set(
                    							current,
                    							"rollbackEnabled",
                    							lv_rollbackEnabled_17_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4561:3: (otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) ) )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==135) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // InternalMlopsDsl.g:4562:4: otherlv_18= 'approvalMode' otherlv_19= '=' ( (lv_approvalMode_20_0= RULE_STRING ) )
                    {
                    otherlv_18=(Token)match(input,135,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getModelRegistrySpecAccess().getApprovalModeKeyword_12_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_19, grammarAccess.getModelRegistrySpecAccess().getEqualsSignKeyword_12_1());
                    			
                    // InternalMlopsDsl.g:4570:4: ( (lv_approvalMode_20_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:4571:5: (lv_approvalMode_20_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:4571:5: (lv_approvalMode_20_0= RULE_STRING )
                    // InternalMlopsDsl.g:4572:6: lv_approvalMode_20_0= RULE_STRING
                    {
                    lv_approvalMode_20_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_approvalMode_20_0, grammarAccess.getModelRegistrySpecAccess().getApprovalModeSTRINGTerminalRuleCall_12_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getModelRegistrySpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"approvalMode",
                    							lv_approvalMode_20_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_21=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getModelRegistrySpecAccess().getRightCurlyBracketKeyword_13());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleModelRegistrySpec"


    // $ANTLR start "entryRuleDecisionCriterion"
    // InternalMlopsDsl.g:4597:1: entryRuleDecisionCriterion returns [EObject current=null] : iv_ruleDecisionCriterion= ruleDecisionCriterion EOF ;
    public final EObject entryRuleDecisionCriterion() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecisionCriterion = null;


        try {
            // InternalMlopsDsl.g:4597:58: (iv_ruleDecisionCriterion= ruleDecisionCriterion EOF )
            // InternalMlopsDsl.g:4598:2: iv_ruleDecisionCriterion= ruleDecisionCriterion EOF
            {
             newCompositeNode(grammarAccess.getDecisionCriterionRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDecisionCriterion=ruleDecisionCriterion();

            state._fsp--;

             current =iv_ruleDecisionCriterion; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDecisionCriterion"


    // $ANTLR start "ruleDecisionCriterion"
    // InternalMlopsDsl.g:4604:1: ruleDecisionCriterion returns [EObject current=null] : (otherlv_0= 'DecisionCriterion' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'weight' otherlv_6= '=' ( (lv_weight_7_0= RULE_FLOAT ) ) (otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) ) )? (otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) ) )? (otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) ) )? otherlv_17= '}' ) ;
    public final EObject ruleDecisionCriterion() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_weight_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_minValue_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_maxValue_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_17=null;
        AntlrDatatypeRuleToken lv_required_16_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:4610:2: ( (otherlv_0= 'DecisionCriterion' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'weight' otherlv_6= '=' ( (lv_weight_7_0= RULE_FLOAT ) ) (otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) ) )? (otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) ) )? (otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) ) )? otherlv_17= '}' ) )
            // InternalMlopsDsl.g:4611:2: (otherlv_0= 'DecisionCriterion' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'weight' otherlv_6= '=' ( (lv_weight_7_0= RULE_FLOAT ) ) (otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) ) )? (otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) ) )? (otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) ) )? otherlv_17= '}' )
            {
            // InternalMlopsDsl.g:4611:2: (otherlv_0= 'DecisionCriterion' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'weight' otherlv_6= '=' ( (lv_weight_7_0= RULE_FLOAT ) ) (otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) ) )? (otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) ) )? (otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) ) )? otherlv_17= '}' )
            // InternalMlopsDsl.g:4612:3: otherlv_0= 'DecisionCriterion' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= 'weight' otherlv_6= '=' ( (lv_weight_7_0= RULE_FLOAT ) ) (otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) ) )? (otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) ) )? (otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) ) )? otherlv_17= '}'
            {
            otherlv_0=(Token)match(input,136,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getDecisionCriterionAccess().getDecisionCriterionKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getDecisionCriterionAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getDecisionCriterionAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getDecisionCriterionAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:4628:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:4629:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:4629:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:4630:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_130); 

            					newLeafNode(lv_name_4_0, grammarAccess.getDecisionCriterionAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecisionCriterionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,137,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getDecisionCriterionAccess().getWeightKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_70); 

            			newLeafNode(otherlv_6, grammarAccess.getDecisionCriterionAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:4654:3: ( (lv_weight_7_0= RULE_FLOAT ) )
            // InternalMlopsDsl.g:4655:4: (lv_weight_7_0= RULE_FLOAT )
            {
            // InternalMlopsDsl.g:4655:4: (lv_weight_7_0= RULE_FLOAT )
            // InternalMlopsDsl.g:4656:5: lv_weight_7_0= RULE_FLOAT
            {
            lv_weight_7_0=(Token)match(input,RULE_FLOAT,FOLLOW_131); 

            					newLeafNode(lv_weight_7_0, grammarAccess.getDecisionCriterionAccess().getWeightFLOATTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecisionCriterionRule());
            					}
            					setWithLastConsumed(
            						current,
            						"weight",
            						lv_weight_7_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
            				

            }


            }

            // InternalMlopsDsl.g:4672:3: (otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) ) )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==138) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // InternalMlopsDsl.g:4673:4: otherlv_8= 'minValue' otherlv_9= '=' ( (lv_minValue_10_0= RULE_FLOAT ) )
                    {
                    otherlv_8=(Token)match(input,138,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getDecisionCriterionAccess().getMinValueKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_9, grammarAccess.getDecisionCriterionAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:4681:4: ( (lv_minValue_10_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4682:5: (lv_minValue_10_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4682:5: (lv_minValue_10_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4683:6: lv_minValue_10_0= RULE_FLOAT
                    {
                    lv_minValue_10_0=(Token)match(input,RULE_FLOAT,FOLLOW_132); 

                    						newLeafNode(lv_minValue_10_0, grammarAccess.getDecisionCriterionAccess().getMinValueFLOATTerminalRuleCall_8_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionCriterionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"minValue",
                    							lv_minValue_10_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4700:3: (otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) ) )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==139) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // InternalMlopsDsl.g:4701:4: otherlv_11= 'maxValue' otherlv_12= '=' ( (lv_maxValue_13_0= RULE_FLOAT ) )
                    {
                    otherlv_11=(Token)match(input,139,FOLLOW_6); 

                    				newLeafNode(otherlv_11, grammarAccess.getDecisionCriterionAccess().getMaxValueKeyword_9_0());
                    			
                    otherlv_12=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_12, grammarAccess.getDecisionCriterionAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:4709:4: ( (lv_maxValue_13_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4710:5: (lv_maxValue_13_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4710:5: (lv_maxValue_13_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4711:6: lv_maxValue_13_0= RULE_FLOAT
                    {
                    lv_maxValue_13_0=(Token)match(input,RULE_FLOAT,FOLLOW_133); 

                    						newLeafNode(lv_maxValue_13_0, grammarAccess.getDecisionCriterionAccess().getMaxValueFLOATTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionCriterionRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"maxValue",
                    							lv_maxValue_13_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4728:3: (otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) ) )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==140) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // InternalMlopsDsl.g:4729:4: otherlv_14= 'required' otherlv_15= '=' ( (lv_required_16_0= ruleBooleanValue ) )
                    {
                    otherlv_14=(Token)match(input,140,FOLLOW_6); 

                    				newLeafNode(otherlv_14, grammarAccess.getDecisionCriterionAccess().getRequiredKeyword_10_0());
                    			
                    otherlv_15=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_15, grammarAccess.getDecisionCriterionAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:4737:4: ( (lv_required_16_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4738:5: (lv_required_16_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4738:5: (lv_required_16_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4739:6: lv_required_16_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getDecisionCriterionAccess().getRequiredBooleanValueParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_8);
                    lv_required_16_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDecisionCriterionRule());
                    						}
                    						set(
                    							current,
                    							"required",
                    							lv_required_16_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_17=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_17, grammarAccess.getDecisionCriterionAccess().getRightCurlyBracketKeyword_11());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDecisionCriterion"


    // $ANTLR start "entryRuleDecisionPolicy"
    // InternalMlopsDsl.g:4765:1: entryRuleDecisionPolicy returns [EObject current=null] : iv_ruleDecisionPolicy= ruleDecisionPolicy EOF ;
    public final EObject entryRuleDecisionPolicy() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleDecisionPolicy = null;


        try {
            // InternalMlopsDsl.g:4765:55: (iv_ruleDecisionPolicy= ruleDecisionPolicy EOF )
            // InternalMlopsDsl.g:4766:2: iv_ruleDecisionPolicy= ruleDecisionPolicy EOF
            {
             newCompositeNode(grammarAccess.getDecisionPolicyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleDecisionPolicy=ruleDecisionPolicy();

            state._fsp--;

             current =iv_ruleDecisionPolicy; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleDecisionPolicy"


    // $ANTLR start "ruleDecisionPolicy"
    // InternalMlopsDsl.g:4772:1: ruleDecisionPolicy returns [EObject current=null] : (otherlv_0= 'DecisionPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) ) )? (otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) ) )? (otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) ) )? (otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) ) )? (otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) ) )? otherlv_24= 'criteria' otherlv_25= '=' otherlv_26= '[' ( (lv_criteria_27_0= ruleDecisionCriterion ) ) (otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) ) )* otherlv_30= ']' otherlv_31= '}' ) ;
    public final EObject ruleDecisionPolicy() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_minAccuracy_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_minF1_11_0=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token lv_maxLatencyMs_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_maxCost_17_0=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_maxDrift_20_0=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token lv_minTraceabilityScore_23_0=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        AntlrDatatypeRuleToken lv_autoPromote_5_0 = null;

        EObject lv_criteria_27_0 = null;

        EObject lv_criteria_29_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:4778:2: ( (otherlv_0= 'DecisionPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) ) )? (otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) ) )? (otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) ) )? (otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) ) )? (otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) ) )? otherlv_24= 'criteria' otherlv_25= '=' otherlv_26= '[' ( (lv_criteria_27_0= ruleDecisionCriterion ) ) (otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) ) )* otherlv_30= ']' otherlv_31= '}' ) )
            // InternalMlopsDsl.g:4779:2: (otherlv_0= 'DecisionPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) ) )? (otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) ) )? (otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) ) )? (otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) ) )? (otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) ) )? otherlv_24= 'criteria' otherlv_25= '=' otherlv_26= '[' ( (lv_criteria_27_0= ruleDecisionCriterion ) ) (otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) ) )* otherlv_30= ']' otherlv_31= '}' )
            {
            // InternalMlopsDsl.g:4779:2: (otherlv_0= 'DecisionPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) ) )? (otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) ) )? (otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) ) )? (otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) ) )? (otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) ) )? otherlv_24= 'criteria' otherlv_25= '=' otherlv_26= '[' ( (lv_criteria_27_0= ruleDecisionCriterion ) ) (otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) ) )* otherlv_30= ']' otherlv_31= '}' )
            // InternalMlopsDsl.g:4780:3: otherlv_0= 'DecisionPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) ) )? (otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) ) )? (otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) ) )? (otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) ) )? (otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) ) )? otherlv_24= 'criteria' otherlv_25= '=' otherlv_26= '[' ( (lv_criteria_27_0= ruleDecisionCriterion ) ) (otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) ) )* otherlv_30= ']' otherlv_31= '}'
            {
            otherlv_0=(Token)match(input,141,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getDecisionPolicyAccess().getDecisionPolicyKeyword_0());
            		
            // InternalMlopsDsl.g:4784:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:4785:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:4785:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:4786:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getDecisionPolicyAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getDecisionPolicyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_134); 

            			newLeafNode(otherlv_2, grammarAccess.getDecisionPolicyAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMlopsDsl.g:4806:3: (otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) ) )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==142) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // InternalMlopsDsl.g:4807:4: otherlv_3= 'autoPromote' otherlv_4= '=' ( (lv_autoPromote_5_0= ruleBooleanValue ) )
                    {
                    otherlv_3=(Token)match(input,142,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getDecisionPolicyAccess().getAutoPromoteKeyword_3_0());
                    			
                    otherlv_4=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_4, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_3_1());
                    			
                    // InternalMlopsDsl.g:4815:4: ( (lv_autoPromote_5_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:4816:5: (lv_autoPromote_5_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:4816:5: (lv_autoPromote_5_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:4817:6: lv_autoPromote_5_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getDecisionPolicyAccess().getAutoPromoteBooleanValueParserRuleCall_3_2_0());
                    					
                    pushFollow(FOLLOW_135);
                    lv_autoPromote_5_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getDecisionPolicyRule());
                    						}
                    						set(
                    							current,
                    							"autoPromote",
                    							lv_autoPromote_5_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4835:3: (otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) ) )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==143) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // InternalMlopsDsl.g:4836:4: otherlv_6= 'minAccuracy' otherlv_7= '=' ( (lv_minAccuracy_8_0= RULE_FLOAT ) )
                    {
                    otherlv_6=(Token)match(input,143,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getDecisionPolicyAccess().getMinAccuracyKeyword_4_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_7, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_4_1());
                    			
                    // InternalMlopsDsl.g:4844:4: ( (lv_minAccuracy_8_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4845:5: (lv_minAccuracy_8_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4845:5: (lv_minAccuracy_8_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4846:6: lv_minAccuracy_8_0= RULE_FLOAT
                    {
                    lv_minAccuracy_8_0=(Token)match(input,RULE_FLOAT,FOLLOW_136); 

                    						newLeafNode(lv_minAccuracy_8_0, grammarAccess.getDecisionPolicyAccess().getMinAccuracyFLOATTerminalRuleCall_4_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"minAccuracy",
                    							lv_minAccuracy_8_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4863:3: (otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) ) )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==144) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // InternalMlopsDsl.g:4864:4: otherlv_9= 'minF1' otherlv_10= '=' ( (lv_minF1_11_0= RULE_FLOAT ) )
                    {
                    otherlv_9=(Token)match(input,144,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getDecisionPolicyAccess().getMinF1Keyword_5_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_10, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:4872:4: ( (lv_minF1_11_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4873:5: (lv_minF1_11_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4873:5: (lv_minF1_11_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4874:6: lv_minF1_11_0= RULE_FLOAT
                    {
                    lv_minF1_11_0=(Token)match(input,RULE_FLOAT,FOLLOW_137); 

                    						newLeafNode(lv_minF1_11_0, grammarAccess.getDecisionPolicyAccess().getMinF1FLOATTerminalRuleCall_5_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"minF1",
                    							lv_minF1_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4891:3: (otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) ) )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==145) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // InternalMlopsDsl.g:4892:4: otherlv_12= 'maxLatencyMs' otherlv_13= '=' ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) )
                    {
                    otherlv_12=(Token)match(input,145,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getDecisionPolicyAccess().getMaxLatencyMsKeyword_6_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_13, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:4900:4: ( (lv_maxLatencyMs_14_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4901:5: (lv_maxLatencyMs_14_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4901:5: (lv_maxLatencyMs_14_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4902:6: lv_maxLatencyMs_14_0= RULE_FLOAT
                    {
                    lv_maxLatencyMs_14_0=(Token)match(input,RULE_FLOAT,FOLLOW_138); 

                    						newLeafNode(lv_maxLatencyMs_14_0, grammarAccess.getDecisionPolicyAccess().getMaxLatencyMsFLOATTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"maxLatencyMs",
                    							lv_maxLatencyMs_14_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4919:3: (otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) ) )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==146) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // InternalMlopsDsl.g:4920:4: otherlv_15= 'maxCost' otherlv_16= '=' ( (lv_maxCost_17_0= RULE_FLOAT ) )
                    {
                    otherlv_15=(Token)match(input,146,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getDecisionPolicyAccess().getMaxCostKeyword_7_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_16, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:4928:4: ( (lv_maxCost_17_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4929:5: (lv_maxCost_17_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4929:5: (lv_maxCost_17_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4930:6: lv_maxCost_17_0= RULE_FLOAT
                    {
                    lv_maxCost_17_0=(Token)match(input,RULE_FLOAT,FOLLOW_139); 

                    						newLeafNode(lv_maxCost_17_0, grammarAccess.getDecisionPolicyAccess().getMaxCostFLOATTerminalRuleCall_7_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"maxCost",
                    							lv_maxCost_17_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4947:3: (otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) ) )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==147) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // InternalMlopsDsl.g:4948:4: otherlv_18= 'maxDrift' otherlv_19= '=' ( (lv_maxDrift_20_0= RULE_FLOAT ) )
                    {
                    otherlv_18=(Token)match(input,147,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getDecisionPolicyAccess().getMaxDriftKeyword_8_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_19, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:4956:4: ( (lv_maxDrift_20_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4957:5: (lv_maxDrift_20_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4957:5: (lv_maxDrift_20_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4958:6: lv_maxDrift_20_0= RULE_FLOAT
                    {
                    lv_maxDrift_20_0=(Token)match(input,RULE_FLOAT,FOLLOW_140); 

                    						newLeafNode(lv_maxDrift_20_0, grammarAccess.getDecisionPolicyAccess().getMaxDriftFLOATTerminalRuleCall_8_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"maxDrift",
                    							lv_maxDrift_20_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:4975:3: (otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) ) )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==148) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // InternalMlopsDsl.g:4976:4: otherlv_21= 'minTraceabilityScore' otherlv_22= '=' ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) )
                    {
                    otherlv_21=(Token)match(input,148,FOLLOW_6); 

                    				newLeafNode(otherlv_21, grammarAccess.getDecisionPolicyAccess().getMinTraceabilityScoreKeyword_9_0());
                    			
                    otherlv_22=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_22, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:4984:4: ( (lv_minTraceabilityScore_23_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:4985:5: (lv_minTraceabilityScore_23_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:4985:5: (lv_minTraceabilityScore_23_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:4986:6: lv_minTraceabilityScore_23_0= RULE_FLOAT
                    {
                    lv_minTraceabilityScore_23_0=(Token)match(input,RULE_FLOAT,FOLLOW_141); 

                    						newLeafNode(lv_minTraceabilityScore_23_0, grammarAccess.getDecisionPolicyAccess().getMinTraceabilityScoreFLOATTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getDecisionPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"minTraceabilityScore",
                    							lv_minTraceabilityScore_23_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_24=(Token)match(input,149,FOLLOW_6); 

            			newLeafNode(otherlv_24, grammarAccess.getDecisionPolicyAccess().getCriteriaKeyword_10());
            		
            otherlv_25=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_25, grammarAccess.getDecisionPolicyAccess().getEqualsSignKeyword_11());
            		
            otherlv_26=(Token)match(input,35,FOLLOW_142); 

            			newLeafNode(otherlv_26, grammarAccess.getDecisionPolicyAccess().getLeftSquareBracketKeyword_12());
            		
            // InternalMlopsDsl.g:5015:3: ( (lv_criteria_27_0= ruleDecisionCriterion ) )
            // InternalMlopsDsl.g:5016:4: (lv_criteria_27_0= ruleDecisionCriterion )
            {
            // InternalMlopsDsl.g:5016:4: (lv_criteria_27_0= ruleDecisionCriterion )
            // InternalMlopsDsl.g:5017:5: lv_criteria_27_0= ruleDecisionCriterion
            {

            					newCompositeNode(grammarAccess.getDecisionPolicyAccess().getCriteriaDecisionCriterionParserRuleCall_13_0());
            				
            pushFollow(FOLLOW_26);
            lv_criteria_27_0=ruleDecisionCriterion();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getDecisionPolicyRule());
            					}
            					add(
            						current,
            						"criteria",
            						lv_criteria_27_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.DecisionCriterion");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:5034:3: (otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) ) )*
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==36) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // InternalMlopsDsl.g:5035:4: otherlv_28= ',' ( (lv_criteria_29_0= ruleDecisionCriterion ) )
            	    {
            	    otherlv_28=(Token)match(input,36,FOLLOW_142); 

            	    				newLeafNode(otherlv_28, grammarAccess.getDecisionPolicyAccess().getCommaKeyword_14_0());
            	    			
            	    // InternalMlopsDsl.g:5039:4: ( (lv_criteria_29_0= ruleDecisionCriterion ) )
            	    // InternalMlopsDsl.g:5040:5: (lv_criteria_29_0= ruleDecisionCriterion )
            	    {
            	    // InternalMlopsDsl.g:5040:5: (lv_criteria_29_0= ruleDecisionCriterion )
            	    // InternalMlopsDsl.g:5041:6: lv_criteria_29_0= ruleDecisionCriterion
            	    {

            	    						newCompositeNode(grammarAccess.getDecisionPolicyAccess().getCriteriaDecisionCriterionParserRuleCall_14_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_criteria_29_0=ruleDecisionCriterion();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getDecisionPolicyRule());
            	    						}
            	    						add(
            	    							current,
            	    							"criteria",
            	    							lv_criteria_29_0,
            	    							"org.xtext.ucd.mlopsdsl.MlopsDsl.DecisionCriterion");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop89;
                }
            } while (true);

            otherlv_30=(Token)match(input,37,FOLLOW_8); 

            			newLeafNode(otherlv_30, grammarAccess.getDecisionPolicyAccess().getRightSquareBracketKeyword_15());
            		
            otherlv_31=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_31, grammarAccess.getDecisionPolicyAccess().getRightCurlyBracketKeyword_16());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDecisionPolicy"


    // $ANTLR start "entryRuleRetrainingPolicy"
    // InternalMlopsDsl.g:5071:1: entryRuleRetrainingPolicy returns [EObject current=null] : iv_ruleRetrainingPolicy= ruleRetrainingPolicy EOF ;
    public final EObject entryRuleRetrainingPolicy() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleRetrainingPolicy = null;


        try {
            // InternalMlopsDsl.g:5071:57: (iv_ruleRetrainingPolicy= ruleRetrainingPolicy EOF )
            // InternalMlopsDsl.g:5072:2: iv_ruleRetrainingPolicy= ruleRetrainingPolicy EOF
            {
             newCompositeNode(grammarAccess.getRetrainingPolicyRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleRetrainingPolicy=ruleRetrainingPolicy();

            state._fsp--;

             current =iv_ruleRetrainingPolicy; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleRetrainingPolicy"


    // $ANTLR start "ruleRetrainingPolicy"
    // InternalMlopsDsl.g:5078:1: ruleRetrainingPolicy returns [EObject current=null] : (otherlv_0= 'RetrainingPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy' otherlv_4= '=' ( (lv_strategy_5_0= ruleRetrainingStrategy ) ) otherlv_6= 'driftThreshold' otherlv_7= '=' ( (lv_driftThreshold_8_0= RULE_FLOAT ) ) (otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) ) )? (otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) ;
    public final EObject ruleRetrainingPolicy() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_driftThreshold_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_performanceDropThreshold_11_0=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token lv_schedule_14_0=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_retrainingQueue_20_0=null;
        Token otherlv_21=null;
        Enumerator lv_strategy_5_0 = null;

        AntlrDatatypeRuleToken lv_autoTrigger_17_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:5084:2: ( (otherlv_0= 'RetrainingPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy' otherlv_4= '=' ( (lv_strategy_5_0= ruleRetrainingStrategy ) ) otherlv_6= 'driftThreshold' otherlv_7= '=' ( (lv_driftThreshold_8_0= RULE_FLOAT ) ) (otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) ) )? (otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) ) )? otherlv_21= '}' ) )
            // InternalMlopsDsl.g:5085:2: (otherlv_0= 'RetrainingPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy' otherlv_4= '=' ( (lv_strategy_5_0= ruleRetrainingStrategy ) ) otherlv_6= 'driftThreshold' otherlv_7= '=' ( (lv_driftThreshold_8_0= RULE_FLOAT ) ) (otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) ) )? (otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            {
            // InternalMlopsDsl.g:5085:2: (otherlv_0= 'RetrainingPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy' otherlv_4= '=' ( (lv_strategy_5_0= ruleRetrainingStrategy ) ) otherlv_6= 'driftThreshold' otherlv_7= '=' ( (lv_driftThreshold_8_0= RULE_FLOAT ) ) (otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) ) )? (otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) ) )? otherlv_21= '}' )
            // InternalMlopsDsl.g:5086:3: otherlv_0= 'RetrainingPolicy' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'strategy' otherlv_4= '=' ( (lv_strategy_5_0= ruleRetrainingStrategy ) ) otherlv_6= 'driftThreshold' otherlv_7= '=' ( (lv_driftThreshold_8_0= RULE_FLOAT ) ) (otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) ) )? (otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) ) )? (otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) ) )? (otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) ) )? otherlv_21= '}'
            {
            otherlv_0=(Token)match(input,150,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getRetrainingPolicyAccess().getRetrainingPolicyKeyword_0());
            		
            // InternalMlopsDsl.g:5090:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:5091:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:5091:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:5092:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getRetrainingPolicyAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRetrainingPolicyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_143); 

            			newLeafNode(otherlv_2, grammarAccess.getRetrainingPolicyAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,151,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getRetrainingPolicyAccess().getStrategyKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_144); 

            			newLeafNode(otherlv_4, grammarAccess.getRetrainingPolicyAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:5120:3: ( (lv_strategy_5_0= ruleRetrainingStrategy ) )
            // InternalMlopsDsl.g:5121:4: (lv_strategy_5_0= ruleRetrainingStrategy )
            {
            // InternalMlopsDsl.g:5121:4: (lv_strategy_5_0= ruleRetrainingStrategy )
            // InternalMlopsDsl.g:5122:5: lv_strategy_5_0= ruleRetrainingStrategy
            {

            					newCompositeNode(grammarAccess.getRetrainingPolicyAccess().getStrategyRetrainingStrategyEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_145);
            lv_strategy_5_0=ruleRetrainingStrategy();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getRetrainingPolicyRule());
            					}
            					set(
            						current,
            						"strategy",
            						lv_strategy_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.RetrainingStrategy");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,152,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getRetrainingPolicyAccess().getDriftThresholdKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_70); 

            			newLeafNode(otherlv_7, grammarAccess.getRetrainingPolicyAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:5147:3: ( (lv_driftThreshold_8_0= RULE_FLOAT ) )
            // InternalMlopsDsl.g:5148:4: (lv_driftThreshold_8_0= RULE_FLOAT )
            {
            // InternalMlopsDsl.g:5148:4: (lv_driftThreshold_8_0= RULE_FLOAT )
            // InternalMlopsDsl.g:5149:5: lv_driftThreshold_8_0= RULE_FLOAT
            {
            lv_driftThreshold_8_0=(Token)match(input,RULE_FLOAT,FOLLOW_146); 

            					newLeafNode(lv_driftThreshold_8_0, grammarAccess.getRetrainingPolicyAccess().getDriftThresholdFLOATTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getRetrainingPolicyRule());
            					}
            					setWithLastConsumed(
            						current,
            						"driftThreshold",
            						lv_driftThreshold_8_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
            				

            }


            }

            // InternalMlopsDsl.g:5165:3: (otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) ) )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==153) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // InternalMlopsDsl.g:5166:4: otherlv_9= 'performanceDropThreshold' otherlv_10= '=' ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) )
                    {
                    otherlv_9=(Token)match(input,153,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getRetrainingPolicyAccess().getPerformanceDropThresholdKeyword_9_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_70); 

                    				newLeafNode(otherlv_10, grammarAccess.getRetrainingPolicyAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:5174:4: ( (lv_performanceDropThreshold_11_0= RULE_FLOAT ) )
                    // InternalMlopsDsl.g:5175:5: (lv_performanceDropThreshold_11_0= RULE_FLOAT )
                    {
                    // InternalMlopsDsl.g:5175:5: (lv_performanceDropThreshold_11_0= RULE_FLOAT )
                    // InternalMlopsDsl.g:5176:6: lv_performanceDropThreshold_11_0= RULE_FLOAT
                    {
                    lv_performanceDropThreshold_11_0=(Token)match(input,RULE_FLOAT,FOLLOW_147); 

                    						newLeafNode(lv_performanceDropThreshold_11_0, grammarAccess.getRetrainingPolicyAccess().getPerformanceDropThresholdFLOATTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRetrainingPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"performanceDropThreshold",
                    							lv_performanceDropThreshold_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.FLOAT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5193:3: (otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) ) )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==154) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // InternalMlopsDsl.g:5194:4: otherlv_12= 'schedule' otherlv_13= '=' ( (lv_schedule_14_0= RULE_STRING ) )
                    {
                    otherlv_12=(Token)match(input,154,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getRetrainingPolicyAccess().getScheduleKeyword_10_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_13, grammarAccess.getRetrainingPolicyAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:5202:4: ( (lv_schedule_14_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:5203:5: (lv_schedule_14_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:5203:5: (lv_schedule_14_0= RULE_STRING )
                    // InternalMlopsDsl.g:5204:6: lv_schedule_14_0= RULE_STRING
                    {
                    lv_schedule_14_0=(Token)match(input,RULE_STRING,FOLLOW_148); 

                    						newLeafNode(lv_schedule_14_0, grammarAccess.getRetrainingPolicyAccess().getScheduleSTRINGTerminalRuleCall_10_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRetrainingPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"schedule",
                    							lv_schedule_14_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5221:3: (otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) ) )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==155) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // InternalMlopsDsl.g:5222:4: otherlv_15= 'autoTrigger' otherlv_16= '=' ( (lv_autoTrigger_17_0= ruleBooleanValue ) )
                    {
                    otherlv_15=(Token)match(input,155,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getRetrainingPolicyAccess().getAutoTriggerKeyword_11_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_16, grammarAccess.getRetrainingPolicyAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:5230:4: ( (lv_autoTrigger_17_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5231:5: (lv_autoTrigger_17_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5231:5: (lv_autoTrigger_17_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5232:6: lv_autoTrigger_17_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getRetrainingPolicyAccess().getAutoTriggerBooleanValueParserRuleCall_11_2_0());
                    					
                    pushFollow(FOLLOW_149);
                    lv_autoTrigger_17_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getRetrainingPolicyRule());
                    						}
                    						set(
                    							current,
                    							"autoTrigger",
                    							lv_autoTrigger_17_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5250:3: (otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) ) )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==156) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // InternalMlopsDsl.g:5251:4: otherlv_18= 'retrainingQueue' otherlv_19= '=' ( (lv_retrainingQueue_20_0= RULE_STRING ) )
                    {
                    otherlv_18=(Token)match(input,156,FOLLOW_6); 

                    				newLeafNode(otherlv_18, grammarAccess.getRetrainingPolicyAccess().getRetrainingQueueKeyword_12_0());
                    			
                    otherlv_19=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_19, grammarAccess.getRetrainingPolicyAccess().getEqualsSignKeyword_12_1());
                    			
                    // InternalMlopsDsl.g:5259:4: ( (lv_retrainingQueue_20_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:5260:5: (lv_retrainingQueue_20_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:5260:5: (lv_retrainingQueue_20_0= RULE_STRING )
                    // InternalMlopsDsl.g:5261:6: lv_retrainingQueue_20_0= RULE_STRING
                    {
                    lv_retrainingQueue_20_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_retrainingQueue_20_0, grammarAccess.getRetrainingPolicyAccess().getRetrainingQueueSTRINGTerminalRuleCall_12_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getRetrainingPolicyRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"retrainingQueue",
                    							lv_retrainingQueue_20_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_21=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_21, grammarAccess.getRetrainingPolicyAccess().getRightCurlyBracketKeyword_13());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRetrainingPolicy"


    // $ANTLR start "entryRuleGovernanceSpec"
    // InternalMlopsDsl.g:5286:1: entryRuleGovernanceSpec returns [EObject current=null] : iv_ruleGovernanceSpec= ruleGovernanceSpec EOF ;
    public final EObject entryRuleGovernanceSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleGovernanceSpec = null;


        try {
            // InternalMlopsDsl.g:5286:55: (iv_ruleGovernanceSpec= ruleGovernanceSpec EOF )
            // InternalMlopsDsl.g:5287:2: iv_ruleGovernanceSpec= ruleGovernanceSpec EOF
            {
             newCompositeNode(grammarAccess.getGovernanceSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleGovernanceSpec=ruleGovernanceSpec();

            state._fsp--;

             current =iv_ruleGovernanceSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleGovernanceSpec"


    // $ANTLR start "ruleGovernanceSpec"
    // InternalMlopsDsl.g:5293:1: ruleGovernanceSpec returns [EObject current=null] : (otherlv_0= 'GovernanceSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) ) )? otherlv_18= '}' ) ;
    public final EObject ruleGovernanceSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_complianceFramework_17_0=null;
        Token otherlv_18=null;
        AntlrDatatypeRuleToken lv_traceabilityRequired_5_0 = null;

        AntlrDatatypeRuleToken lv_explainDecision_8_0 = null;

        AntlrDatatypeRuleToken lv_auditTrail_11_0 = null;

        AntlrDatatypeRuleToken lv_riskReport_14_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:5299:2: ( (otherlv_0= 'GovernanceSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) ) )? otherlv_18= '}' ) )
            // InternalMlopsDsl.g:5300:2: (otherlv_0= 'GovernanceSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) ) )? otherlv_18= '}' )
            {
            // InternalMlopsDsl.g:5300:2: (otherlv_0= 'GovernanceSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) ) )? otherlv_18= '}' )
            // InternalMlopsDsl.g:5301:3: otherlv_0= 'GovernanceSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' (otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) ) )? (otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) ) )? (otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) ) )? otherlv_18= '}'
            {
            otherlv_0=(Token)match(input,157,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getGovernanceSpecAccess().getGovernanceSpecKeyword_0());
            		
            // InternalMlopsDsl.g:5305:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:5306:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:5306:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:5307:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getGovernanceSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getGovernanceSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_150); 

            			newLeafNode(otherlv_2, grammarAccess.getGovernanceSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            // InternalMlopsDsl.g:5327:3: (otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) ) )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==158) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // InternalMlopsDsl.g:5328:4: otherlv_3= 'traceabilityRequired' otherlv_4= '=' ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) )
                    {
                    otherlv_3=(Token)match(input,158,FOLLOW_6); 

                    				newLeafNode(otherlv_3, grammarAccess.getGovernanceSpecAccess().getTraceabilityRequiredKeyword_3_0());
                    			
                    otherlv_4=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_4, grammarAccess.getGovernanceSpecAccess().getEqualsSignKeyword_3_1());
                    			
                    // InternalMlopsDsl.g:5336:4: ( (lv_traceabilityRequired_5_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5337:5: (lv_traceabilityRequired_5_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5337:5: (lv_traceabilityRequired_5_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5338:6: lv_traceabilityRequired_5_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getGovernanceSpecAccess().getTraceabilityRequiredBooleanValueParserRuleCall_3_2_0());
                    					
                    pushFollow(FOLLOW_151);
                    lv_traceabilityRequired_5_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGovernanceSpecRule());
                    						}
                    						set(
                    							current,
                    							"traceabilityRequired",
                    							lv_traceabilityRequired_5_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5356:3: (otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) ) )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==159) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // InternalMlopsDsl.g:5357:4: otherlv_6= 'explainDecision' otherlv_7= '=' ( (lv_explainDecision_8_0= ruleBooleanValue ) )
                    {
                    otherlv_6=(Token)match(input,159,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getGovernanceSpecAccess().getExplainDecisionKeyword_4_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_7, grammarAccess.getGovernanceSpecAccess().getEqualsSignKeyword_4_1());
                    			
                    // InternalMlopsDsl.g:5365:4: ( (lv_explainDecision_8_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5366:5: (lv_explainDecision_8_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5366:5: (lv_explainDecision_8_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5367:6: lv_explainDecision_8_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getGovernanceSpecAccess().getExplainDecisionBooleanValueParserRuleCall_4_2_0());
                    					
                    pushFollow(FOLLOW_152);
                    lv_explainDecision_8_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGovernanceSpecRule());
                    						}
                    						set(
                    							current,
                    							"explainDecision",
                    							lv_explainDecision_8_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5385:3: (otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) ) )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==160) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // InternalMlopsDsl.g:5386:4: otherlv_9= 'auditTrail' otherlv_10= '=' ( (lv_auditTrail_11_0= ruleBooleanValue ) )
                    {
                    otherlv_9=(Token)match(input,160,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getGovernanceSpecAccess().getAuditTrailKeyword_5_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_10, grammarAccess.getGovernanceSpecAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:5394:4: ( (lv_auditTrail_11_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5395:5: (lv_auditTrail_11_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5395:5: (lv_auditTrail_11_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5396:6: lv_auditTrail_11_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getGovernanceSpecAccess().getAuditTrailBooleanValueParserRuleCall_5_2_0());
                    					
                    pushFollow(FOLLOW_153);
                    lv_auditTrail_11_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGovernanceSpecRule());
                    						}
                    						set(
                    							current,
                    							"auditTrail",
                    							lv_auditTrail_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5414:3: (otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) ) )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==161) ) {
                alt97=1;
            }
            switch (alt97) {
                case 1 :
                    // InternalMlopsDsl.g:5415:4: otherlv_12= 'riskReport' otherlv_13= '=' ( (lv_riskReport_14_0= ruleBooleanValue ) )
                    {
                    otherlv_12=(Token)match(input,161,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getGovernanceSpecAccess().getRiskReportKeyword_6_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_13, grammarAccess.getGovernanceSpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:5423:4: ( (lv_riskReport_14_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5424:5: (lv_riskReport_14_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5424:5: (lv_riskReport_14_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5425:6: lv_riskReport_14_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getGovernanceSpecAccess().getRiskReportBooleanValueParserRuleCall_6_2_0());
                    					
                    pushFollow(FOLLOW_154);
                    lv_riskReport_14_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getGovernanceSpecRule());
                    						}
                    						set(
                    							current,
                    							"riskReport",
                    							lv_riskReport_14_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5443:3: (otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) ) )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==162) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // InternalMlopsDsl.g:5444:4: otherlv_15= 'complianceFramework' otherlv_16= '=' ( (lv_complianceFramework_17_0= RULE_STRING ) )
                    {
                    otherlv_15=(Token)match(input,162,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getGovernanceSpecAccess().getComplianceFrameworkKeyword_7_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_16, grammarAccess.getGovernanceSpecAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:5452:4: ( (lv_complianceFramework_17_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:5453:5: (lv_complianceFramework_17_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:5453:5: (lv_complianceFramework_17_0= RULE_STRING )
                    // InternalMlopsDsl.g:5454:6: lv_complianceFramework_17_0= RULE_STRING
                    {
                    lv_complianceFramework_17_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_complianceFramework_17_0, grammarAccess.getGovernanceSpecAccess().getComplianceFrameworkSTRINGTerminalRuleCall_7_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getGovernanceSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"complianceFramework",
                    							lv_complianceFramework_17_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_18=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_18, grammarAccess.getGovernanceSpecAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleGovernanceSpec"


    // $ANTLR start "entryRulePipelineStage"
    // InternalMlopsDsl.g:5479:1: entryRulePipelineStage returns [EObject current=null] : iv_rulePipelineStage= rulePipelineStage EOF ;
    public final EObject entryRulePipelineStage() throws RecognitionException {
        EObject current = null;

        EObject iv_rulePipelineStage = null;


        try {
            // InternalMlopsDsl.g:5479:54: (iv_rulePipelineStage= rulePipelineStage EOF )
            // InternalMlopsDsl.g:5480:2: iv_rulePipelineStage= rulePipelineStage EOF
            {
             newCompositeNode(grammarAccess.getPipelineStageRule()); 
            pushFollow(FOLLOW_1);
            iv_rulePipelineStage=rulePipelineStage();

            state._fsp--;

             current =iv_rulePipelineStage; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRulePipelineStage"


    // $ANTLR start "rulePipelineStage"
    // InternalMlopsDsl.g:5486:1: rulePipelineStage returns [EObject current=null] : (otherlv_0= 'PipelineStage' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= rulePipelineStageKind ) ) otherlv_5= 'name' otherlv_6= '=' ( (lv_name_7_0= RULE_STRING ) ) (otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) ) )? (otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) ) )? (otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) ) )? otherlv_17= '}' ) ;
    public final EObject rulePipelineStage() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_name_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_tool_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_outputArtifact_16_0=null;
        Token otherlv_17=null;
        Enumerator lv_kind_4_0 = null;

        AntlrDatatypeRuleToken lv_required_10_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:5492:2: ( (otherlv_0= 'PipelineStage' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= rulePipelineStageKind ) ) otherlv_5= 'name' otherlv_6= '=' ( (lv_name_7_0= RULE_STRING ) ) (otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) ) )? (otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) ) )? (otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) ) )? otherlv_17= '}' ) )
            // InternalMlopsDsl.g:5493:2: (otherlv_0= 'PipelineStage' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= rulePipelineStageKind ) ) otherlv_5= 'name' otherlv_6= '=' ( (lv_name_7_0= RULE_STRING ) ) (otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) ) )? (otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) ) )? (otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) ) )? otherlv_17= '}' )
            {
            // InternalMlopsDsl.g:5493:2: (otherlv_0= 'PipelineStage' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= rulePipelineStageKind ) ) otherlv_5= 'name' otherlv_6= '=' ( (lv_name_7_0= RULE_STRING ) ) (otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) ) )? (otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) ) )? (otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) ) )? otherlv_17= '}' )
            // InternalMlopsDsl.g:5494:3: otherlv_0= 'PipelineStage' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= rulePipelineStageKind ) ) otherlv_5= 'name' otherlv_6= '=' ( (lv_name_7_0= RULE_STRING ) ) (otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) ) )? (otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) ) )? (otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) ) )? otherlv_17= '}'
            {
            otherlv_0=(Token)match(input,163,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getPipelineStageAccess().getPipelineStageKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getPipelineStageAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getPipelineStageAccess().getKindKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_155); 

            			newLeafNode(otherlv_3, grammarAccess.getPipelineStageAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:5510:3: ( (lv_kind_4_0= rulePipelineStageKind ) )
            // InternalMlopsDsl.g:5511:4: (lv_kind_4_0= rulePipelineStageKind )
            {
            // InternalMlopsDsl.g:5511:4: (lv_kind_4_0= rulePipelineStageKind )
            // InternalMlopsDsl.g:5512:5: lv_kind_4_0= rulePipelineStageKind
            {

            					newCompositeNode(grammarAccess.getPipelineStageAccess().getKindPipelineStageKindEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_5);
            lv_kind_4_0=rulePipelineStageKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getPipelineStageRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_4_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.PipelineStageKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getPipelineStageAccess().getNameKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_6, grammarAccess.getPipelineStageAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:5537:3: ( (lv_name_7_0= RULE_STRING ) )
            // InternalMlopsDsl.g:5538:4: (lv_name_7_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:5538:4: (lv_name_7_0= RULE_STRING )
            // InternalMlopsDsl.g:5539:5: lv_name_7_0= RULE_STRING
            {
            lv_name_7_0=(Token)match(input,RULE_STRING,FOLLOW_156); 

            					newLeafNode(lv_name_7_0, grammarAccess.getPipelineStageAccess().getNameSTRINGTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getPipelineStageRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_7_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:5555:3: (otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) ) )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==140) ) {
                alt99=1;
            }
            switch (alt99) {
                case 1 :
                    // InternalMlopsDsl.g:5556:4: otherlv_8= 'required' otherlv_9= '=' ( (lv_required_10_0= ruleBooleanValue ) )
                    {
                    otherlv_8=(Token)match(input,140,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getPipelineStageAccess().getRequiredKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_9, grammarAccess.getPipelineStageAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:5564:4: ( (lv_required_10_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5565:5: (lv_required_10_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5565:5: (lv_required_10_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5566:6: lv_required_10_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getPipelineStageAccess().getRequiredBooleanValueParserRuleCall_8_2_0());
                    					
                    pushFollow(FOLLOW_157);
                    lv_required_10_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getPipelineStageRule());
                    						}
                    						set(
                    							current,
                    							"required",
                    							lv_required_10_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5584:3: (otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) ) )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==164) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // InternalMlopsDsl.g:5585:4: otherlv_11= 'tool' otherlv_12= '=' ( (lv_tool_13_0= RULE_STRING ) )
                    {
                    otherlv_11=(Token)match(input,164,FOLLOW_6); 

                    				newLeafNode(otherlv_11, grammarAccess.getPipelineStageAccess().getToolKeyword_9_0());
                    			
                    otherlv_12=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_12, grammarAccess.getPipelineStageAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:5593:4: ( (lv_tool_13_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:5594:5: (lv_tool_13_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:5594:5: (lv_tool_13_0= RULE_STRING )
                    // InternalMlopsDsl.g:5595:6: lv_tool_13_0= RULE_STRING
                    {
                    lv_tool_13_0=(Token)match(input,RULE_STRING,FOLLOW_158); 

                    						newLeafNode(lv_tool_13_0, grammarAccess.getPipelineStageAccess().getToolSTRINGTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPipelineStageRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"tool",
                    							lv_tool_13_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5612:3: (otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) ) )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==165) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // InternalMlopsDsl.g:5613:4: otherlv_14= 'outputArtifact' otherlv_15= '=' ( (lv_outputArtifact_16_0= RULE_STRING ) )
                    {
                    otherlv_14=(Token)match(input,165,FOLLOW_6); 

                    				newLeafNode(otherlv_14, grammarAccess.getPipelineStageAccess().getOutputArtifactKeyword_10_0());
                    			
                    otherlv_15=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_15, grammarAccess.getPipelineStageAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:5621:4: ( (lv_outputArtifact_16_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:5622:5: (lv_outputArtifact_16_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:5622:5: (lv_outputArtifact_16_0= RULE_STRING )
                    // InternalMlopsDsl.g:5623:6: lv_outputArtifact_16_0= RULE_STRING
                    {
                    lv_outputArtifact_16_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_outputArtifact_16_0, grammarAccess.getPipelineStageAccess().getOutputArtifactSTRINGTerminalRuleCall_10_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getPipelineStageRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"outputArtifact",
                    							lv_outputArtifact_16_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_17=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_17, grammarAccess.getPipelineStageAccess().getRightCurlyBracketKeyword_11());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePipelineStage"


    // $ANTLR start "entryRuleCiCdCtSpec"
    // InternalMlopsDsl.g:5648:1: entryRuleCiCdCtSpec returns [EObject current=null] : iv_ruleCiCdCtSpec= ruleCiCdCtSpec EOF ;
    public final EObject entryRuleCiCdCtSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleCiCdCtSpec = null;


        try {
            // InternalMlopsDsl.g:5648:51: (iv_ruleCiCdCtSpec= ruleCiCdCtSpec EOF )
            // InternalMlopsDsl.g:5649:2: iv_ruleCiCdCtSpec= ruleCiCdCtSpec EOF
            {
             newCompositeNode(grammarAccess.getCiCdCtSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleCiCdCtSpec=ruleCiCdCtSpec();

            state._fsp--;

             current =iv_ruleCiCdCtSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleCiCdCtSpec"


    // $ANTLR start "ruleCiCdCtSpec"
    // InternalMlopsDsl.g:5655:1: ruleCiCdCtSpec returns [EObject current=null] : (otherlv_0= 'CiCdCtSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'target' otherlv_4= '=' ( (lv_target_5_0= ruleCITarget ) ) otherlv_6= 'pipelineFile' otherlv_7= '=' ( (lv_pipelineFile_8_0= RULE_STRING ) ) (otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) ) )? otherlv_18= 'stages' otherlv_19= '=' otherlv_20= '[' ( (lv_stages_21_0= rulePipelineStage ) ) (otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) ) )* otherlv_24= ']' otherlv_25= '}' ) ;
    public final EObject ruleCiCdCtSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_pipelineFile_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token otherlv_20=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Enumerator lv_target_5_0 = null;

        AntlrDatatypeRuleToken lv_enableCI_11_0 = null;

        AntlrDatatypeRuleToken lv_enableCD_14_0 = null;

        AntlrDatatypeRuleToken lv_enableCT_17_0 = null;

        EObject lv_stages_21_0 = null;

        EObject lv_stages_23_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:5661:2: ( (otherlv_0= 'CiCdCtSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'target' otherlv_4= '=' ( (lv_target_5_0= ruleCITarget ) ) otherlv_6= 'pipelineFile' otherlv_7= '=' ( (lv_pipelineFile_8_0= RULE_STRING ) ) (otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) ) )? otherlv_18= 'stages' otherlv_19= '=' otherlv_20= '[' ( (lv_stages_21_0= rulePipelineStage ) ) (otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) ) )* otherlv_24= ']' otherlv_25= '}' ) )
            // InternalMlopsDsl.g:5662:2: (otherlv_0= 'CiCdCtSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'target' otherlv_4= '=' ( (lv_target_5_0= ruleCITarget ) ) otherlv_6= 'pipelineFile' otherlv_7= '=' ( (lv_pipelineFile_8_0= RULE_STRING ) ) (otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) ) )? otherlv_18= 'stages' otherlv_19= '=' otherlv_20= '[' ( (lv_stages_21_0= rulePipelineStage ) ) (otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) ) )* otherlv_24= ']' otherlv_25= '}' )
            {
            // InternalMlopsDsl.g:5662:2: (otherlv_0= 'CiCdCtSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'target' otherlv_4= '=' ( (lv_target_5_0= ruleCITarget ) ) otherlv_6= 'pipelineFile' otherlv_7= '=' ( (lv_pipelineFile_8_0= RULE_STRING ) ) (otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) ) )? otherlv_18= 'stages' otherlv_19= '=' otherlv_20= '[' ( (lv_stages_21_0= rulePipelineStage ) ) (otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) ) )* otherlv_24= ']' otherlv_25= '}' )
            // InternalMlopsDsl.g:5663:3: otherlv_0= 'CiCdCtSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'target' otherlv_4= '=' ( (lv_target_5_0= ruleCITarget ) ) otherlv_6= 'pipelineFile' otherlv_7= '=' ( (lv_pipelineFile_8_0= RULE_STRING ) ) (otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) ) )? (otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) ) )? (otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) ) )? otherlv_18= 'stages' otherlv_19= '=' otherlv_20= '[' ( (lv_stages_21_0= rulePipelineStage ) ) (otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) ) )* otherlv_24= ']' otherlv_25= '}'
            {
            otherlv_0=(Token)match(input,166,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getCiCdCtSpecAccess().getCiCdCtSpecKeyword_0());
            		
            // InternalMlopsDsl.g:5667:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:5668:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:5668:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:5669:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getCiCdCtSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCiCdCtSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_159); 

            			newLeafNode(otherlv_2, grammarAccess.getCiCdCtSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,167,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getCiCdCtSpecAccess().getTargetKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_160); 

            			newLeafNode(otherlv_4, grammarAccess.getCiCdCtSpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:5697:3: ( (lv_target_5_0= ruleCITarget ) )
            // InternalMlopsDsl.g:5698:4: (lv_target_5_0= ruleCITarget )
            {
            // InternalMlopsDsl.g:5698:4: (lv_target_5_0= ruleCITarget )
            // InternalMlopsDsl.g:5699:5: lv_target_5_0= ruleCITarget
            {

            					newCompositeNode(grammarAccess.getCiCdCtSpecAccess().getTargetCITargetEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_161);
            lv_target_5_0=ruleCITarget();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCiCdCtSpecRule());
            					}
            					set(
            						current,
            						"target",
            						lv_target_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.CITarget");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_6=(Token)match(input,168,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getCiCdCtSpecAccess().getPipelineFileKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_7, grammarAccess.getCiCdCtSpecAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:5724:3: ( (lv_pipelineFile_8_0= RULE_STRING ) )
            // InternalMlopsDsl.g:5725:4: (lv_pipelineFile_8_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:5725:4: (lv_pipelineFile_8_0= RULE_STRING )
            // InternalMlopsDsl.g:5726:5: lv_pipelineFile_8_0= RULE_STRING
            {
            lv_pipelineFile_8_0=(Token)match(input,RULE_STRING,FOLLOW_162); 

            					newLeafNode(lv_pipelineFile_8_0, grammarAccess.getCiCdCtSpecAccess().getPipelineFileSTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getCiCdCtSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"pipelineFile",
            						lv_pipelineFile_8_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:5742:3: (otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) ) )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==169) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // InternalMlopsDsl.g:5743:4: otherlv_9= 'enableCI' otherlv_10= '=' ( (lv_enableCI_11_0= ruleBooleanValue ) )
                    {
                    otherlv_9=(Token)match(input,169,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getCiCdCtSpecAccess().getEnableCIKeyword_9_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_10, grammarAccess.getCiCdCtSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:5751:4: ( (lv_enableCI_11_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5752:5: (lv_enableCI_11_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5752:5: (lv_enableCI_11_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5753:6: lv_enableCI_11_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getCiCdCtSpecAccess().getEnableCIBooleanValueParserRuleCall_9_2_0());
                    					
                    pushFollow(FOLLOW_163);
                    lv_enableCI_11_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCiCdCtSpecRule());
                    						}
                    						set(
                    							current,
                    							"enableCI",
                    							lv_enableCI_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5771:3: (otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) ) )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==170) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // InternalMlopsDsl.g:5772:4: otherlv_12= 'enableCD' otherlv_13= '=' ( (lv_enableCD_14_0= ruleBooleanValue ) )
                    {
                    otherlv_12=(Token)match(input,170,FOLLOW_6); 

                    				newLeafNode(otherlv_12, grammarAccess.getCiCdCtSpecAccess().getEnableCDKeyword_10_0());
                    			
                    otherlv_13=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_13, grammarAccess.getCiCdCtSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    // InternalMlopsDsl.g:5780:4: ( (lv_enableCD_14_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5781:5: (lv_enableCD_14_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5781:5: (lv_enableCD_14_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5782:6: lv_enableCD_14_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getCiCdCtSpecAccess().getEnableCDBooleanValueParserRuleCall_10_2_0());
                    					
                    pushFollow(FOLLOW_164);
                    lv_enableCD_14_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCiCdCtSpecRule());
                    						}
                    						set(
                    							current,
                    							"enableCD",
                    							lv_enableCD_14_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:5800:3: (otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) ) )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==171) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // InternalMlopsDsl.g:5801:4: otherlv_15= 'enableCT' otherlv_16= '=' ( (lv_enableCT_17_0= ruleBooleanValue ) )
                    {
                    otherlv_15=(Token)match(input,171,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getCiCdCtSpecAccess().getEnableCTKeyword_11_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_16, grammarAccess.getCiCdCtSpecAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:5809:4: ( (lv_enableCT_17_0= ruleBooleanValue ) )
                    // InternalMlopsDsl.g:5810:5: (lv_enableCT_17_0= ruleBooleanValue )
                    {
                    // InternalMlopsDsl.g:5810:5: (lv_enableCT_17_0= ruleBooleanValue )
                    // InternalMlopsDsl.g:5811:6: lv_enableCT_17_0= ruleBooleanValue
                    {

                    						newCompositeNode(grammarAccess.getCiCdCtSpecAccess().getEnableCTBooleanValueParserRuleCall_11_2_0());
                    					
                    pushFollow(FOLLOW_165);
                    lv_enableCT_17_0=ruleBooleanValue();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getCiCdCtSpecRule());
                    						}
                    						set(
                    							current,
                    							"enableCT",
                    							lv_enableCT_17_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.BooleanValue");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_18=(Token)match(input,172,FOLLOW_6); 

            			newLeafNode(otherlv_18, grammarAccess.getCiCdCtSpecAccess().getStagesKeyword_12());
            		
            otherlv_19=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_19, grammarAccess.getCiCdCtSpecAccess().getEqualsSignKeyword_13());
            		
            otherlv_20=(Token)match(input,35,FOLLOW_166); 

            			newLeafNode(otherlv_20, grammarAccess.getCiCdCtSpecAccess().getLeftSquareBracketKeyword_14());
            		
            // InternalMlopsDsl.g:5841:3: ( (lv_stages_21_0= rulePipelineStage ) )
            // InternalMlopsDsl.g:5842:4: (lv_stages_21_0= rulePipelineStage )
            {
            // InternalMlopsDsl.g:5842:4: (lv_stages_21_0= rulePipelineStage )
            // InternalMlopsDsl.g:5843:5: lv_stages_21_0= rulePipelineStage
            {

            					newCompositeNode(grammarAccess.getCiCdCtSpecAccess().getStagesPipelineStageParserRuleCall_15_0());
            				
            pushFollow(FOLLOW_26);
            lv_stages_21_0=rulePipelineStage();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getCiCdCtSpecRule());
            					}
            					add(
            						current,
            						"stages",
            						lv_stages_21_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.PipelineStage");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:5860:3: (otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) ) )*
            loop105:
            do {
                int alt105=2;
                int LA105_0 = input.LA(1);

                if ( (LA105_0==36) ) {
                    alt105=1;
                }


                switch (alt105) {
            	case 1 :
            	    // InternalMlopsDsl.g:5861:4: otherlv_22= ',' ( (lv_stages_23_0= rulePipelineStage ) )
            	    {
            	    otherlv_22=(Token)match(input,36,FOLLOW_166); 

            	    				newLeafNode(otherlv_22, grammarAccess.getCiCdCtSpecAccess().getCommaKeyword_16_0());
            	    			
            	    // InternalMlopsDsl.g:5865:4: ( (lv_stages_23_0= rulePipelineStage ) )
            	    // InternalMlopsDsl.g:5866:5: (lv_stages_23_0= rulePipelineStage )
            	    {
            	    // InternalMlopsDsl.g:5866:5: (lv_stages_23_0= rulePipelineStage )
            	    // InternalMlopsDsl.g:5867:6: lv_stages_23_0= rulePipelineStage
            	    {

            	    						newCompositeNode(grammarAccess.getCiCdCtSpecAccess().getStagesPipelineStageParserRuleCall_16_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_stages_23_0=rulePipelineStage();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getCiCdCtSpecRule());
            	    						}
            	    						add(
            	    							current,
            	    							"stages",
            	    							lv_stages_23_0,
            	    							"org.xtext.ucd.mlopsdsl.MlopsDsl.PipelineStage");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop105;
                }
            } while (true);

            otherlv_24=(Token)match(input,37,FOLLOW_8); 

            			newLeafNode(otherlv_24, grammarAccess.getCiCdCtSpecAccess().getRightSquareBracketKeyword_17());
            		
            otherlv_25=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_25, grammarAccess.getCiCdCtSpecAccess().getRightCurlyBracketKeyword_18());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCiCdCtSpec"


    // $ANTLR start "entryRuleResource"
    // InternalMlopsDsl.g:5897:1: entryRuleResource returns [EObject current=null] : iv_ruleResource= ruleResource EOF ;
    public final EObject entryRuleResource() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleResource = null;


        try {
            // InternalMlopsDsl.g:5897:49: (iv_ruleResource= ruleResource EOF )
            // InternalMlopsDsl.g:5898:2: iv_ruleResource= ruleResource EOF
            {
             newCompositeNode(grammarAccess.getResourceRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleResource=ruleResource();

            state._fsp--;

             current =iv_ruleResource; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleResource"


    // $ANTLR start "ruleResource"
    // InternalMlopsDsl.g:5904:1: ruleResource returns [EObject current=null] : (otherlv_0= 'Resource' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'amount' otherlv_6= '=' ( (lv_amount_7_0= RULE_INT ) ) otherlv_8= '}' ) ;
    public final EObject ruleResource() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_key_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_amount_7_0=null;
        Token otherlv_8=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:5910:2: ( (otherlv_0= 'Resource' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'amount' otherlv_6= '=' ( (lv_amount_7_0= RULE_INT ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:5911:2: (otherlv_0= 'Resource' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'amount' otherlv_6= '=' ( (lv_amount_7_0= RULE_INT ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:5911:2: (otherlv_0= 'Resource' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'amount' otherlv_6= '=' ( (lv_amount_7_0= RULE_INT ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:5912:3: otherlv_0= 'Resource' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'amount' otherlv_6= '=' ( (lv_amount_7_0= RULE_INT ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,173,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getResourceAccess().getResourceKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getResourceAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,20,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getResourceAccess().getKeyKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getResourceAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:5928:3: ( (lv_key_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:5929:4: (lv_key_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:5929:4: (lv_key_4_0= RULE_STRING )
            // InternalMlopsDsl.g:5930:5: lv_key_4_0= RULE_STRING
            {
            lv_key_4_0=(Token)match(input,RULE_STRING,FOLLOW_167); 

            					newLeafNode(lv_key_4_0, grammarAccess.getResourceAccess().getKeySTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getResourceRule());
            					}
            					setWithLastConsumed(
            						current,
            						"key",
            						lv_key_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,174,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getResourceAccess().getAmountKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_17); 

            			newLeafNode(otherlv_6, grammarAccess.getResourceAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:5954:3: ( (lv_amount_7_0= RULE_INT ) )
            // InternalMlopsDsl.g:5955:4: (lv_amount_7_0= RULE_INT )
            {
            // InternalMlopsDsl.g:5955:4: (lv_amount_7_0= RULE_INT )
            // InternalMlopsDsl.g:5956:5: lv_amount_7_0= RULE_INT
            {
            lv_amount_7_0=(Token)match(input,RULE_INT,FOLLOW_8); 

            					newLeafNode(lv_amount_7_0, grammarAccess.getResourceAccess().getAmountINTTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getResourceRule());
            					}
            					setWithLastConsumed(
            						current,
            						"amount",
            						lv_amount_7_0,
            						"org.eclipse.xtext.common.Terminals.INT");
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getResourceAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleResource"


    // $ANTLR start "entryRuleFeatureStoreSpec"
    // InternalMlopsDsl.g:5980:1: entryRuleFeatureStoreSpec returns [EObject current=null] : iv_ruleFeatureStoreSpec= ruleFeatureStoreSpec EOF ;
    public final EObject entryRuleFeatureStoreSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleFeatureStoreSpec = null;


        try {
            // InternalMlopsDsl.g:5980:57: (iv_ruleFeatureStoreSpec= ruleFeatureStoreSpec EOF )
            // InternalMlopsDsl.g:5981:2: iv_ruleFeatureStoreSpec= ruleFeatureStoreSpec EOF
            {
             newCompositeNode(grammarAccess.getFeatureStoreSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleFeatureStoreSpec=ruleFeatureStoreSpec();

            state._fsp--;

             current =iv_ruleFeatureStoreSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleFeatureStoreSpec"


    // $ANTLR start "ruleFeatureStoreSpec"
    // InternalMlopsDsl.g:5987:1: ruleFeatureStoreSpec returns [EObject current=null] : (otherlv_0= 'FeatureStoreSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) otherlv_6= 'offlineStore' otherlv_7= '=' ( (lv_offlineStore_8_0= RULE_STRING ) ) otherlv_9= 'onlineStore' otherlv_10= '=' ( (lv_onlineStore_11_0= RULE_STRING ) ) otherlv_12= '}' ) ;
    public final EObject ruleFeatureStoreSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_kind_5_0=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_offlineStore_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_onlineStore_11_0=null;
        Token otherlv_12=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:5993:2: ( (otherlv_0= 'FeatureStoreSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) otherlv_6= 'offlineStore' otherlv_7= '=' ( (lv_offlineStore_8_0= RULE_STRING ) ) otherlv_9= 'onlineStore' otherlv_10= '=' ( (lv_onlineStore_11_0= RULE_STRING ) ) otherlv_12= '}' ) )
            // InternalMlopsDsl.g:5994:2: (otherlv_0= 'FeatureStoreSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) otherlv_6= 'offlineStore' otherlv_7= '=' ( (lv_offlineStore_8_0= RULE_STRING ) ) otherlv_9= 'onlineStore' otherlv_10= '=' ( (lv_onlineStore_11_0= RULE_STRING ) ) otherlv_12= '}' )
            {
            // InternalMlopsDsl.g:5994:2: (otherlv_0= 'FeatureStoreSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) otherlv_6= 'offlineStore' otherlv_7= '=' ( (lv_offlineStore_8_0= RULE_STRING ) ) otherlv_9= 'onlineStore' otherlv_10= '=' ( (lv_onlineStore_11_0= RULE_STRING ) ) otherlv_12= '}' )
            // InternalMlopsDsl.g:5995:3: otherlv_0= 'FeatureStoreSpec' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'kind' otherlv_4= '=' ( (lv_kind_5_0= RULE_STRING ) ) otherlv_6= 'offlineStore' otherlv_7= '=' ( (lv_offlineStore_8_0= RULE_STRING ) ) otherlv_9= 'onlineStore' otherlv_10= '=' ( (lv_onlineStore_11_0= RULE_STRING ) ) otherlv_12= '}'
            {
            otherlv_0=(Token)match(input,175,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getFeatureStoreSpecAccess().getFeatureStoreSpecKeyword_0());
            		
            // InternalMlopsDsl.g:5999:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:6000:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:6000:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:6001:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getFeatureStoreSpecAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFeatureStoreSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_2, grammarAccess.getFeatureStoreSpecAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getFeatureStoreSpecAccess().getKindKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_4, grammarAccess.getFeatureStoreSpecAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:6029:3: ( (lv_kind_5_0= RULE_STRING ) )
            // InternalMlopsDsl.g:6030:4: (lv_kind_5_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:6030:4: (lv_kind_5_0= RULE_STRING )
            // InternalMlopsDsl.g:6031:5: lv_kind_5_0= RULE_STRING
            {
            lv_kind_5_0=(Token)match(input,RULE_STRING,FOLLOW_168); 

            					newLeafNode(lv_kind_5_0, grammarAccess.getFeatureStoreSpecAccess().getKindSTRINGTerminalRuleCall_5_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFeatureStoreSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"kind",
            						lv_kind_5_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_6=(Token)match(input,176,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getFeatureStoreSpecAccess().getOfflineStoreKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_7, grammarAccess.getFeatureStoreSpecAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:6055:3: ( (lv_offlineStore_8_0= RULE_STRING ) )
            // InternalMlopsDsl.g:6056:4: (lv_offlineStore_8_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:6056:4: (lv_offlineStore_8_0= RULE_STRING )
            // InternalMlopsDsl.g:6057:5: lv_offlineStore_8_0= RULE_STRING
            {
            lv_offlineStore_8_0=(Token)match(input,RULE_STRING,FOLLOW_169); 

            					newLeafNode(lv_offlineStore_8_0, grammarAccess.getFeatureStoreSpecAccess().getOfflineStoreSTRINGTerminalRuleCall_8_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFeatureStoreSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"offlineStore",
            						lv_offlineStore_8_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_9=(Token)match(input,177,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getFeatureStoreSpecAccess().getOnlineStoreKeyword_9());
            		
            otherlv_10=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_10, grammarAccess.getFeatureStoreSpecAccess().getEqualsSignKeyword_10());
            		
            // InternalMlopsDsl.g:6081:3: ( (lv_onlineStore_11_0= RULE_STRING ) )
            // InternalMlopsDsl.g:6082:4: (lv_onlineStore_11_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:6082:4: (lv_onlineStore_11_0= RULE_STRING )
            // InternalMlopsDsl.g:6083:5: lv_onlineStore_11_0= RULE_STRING
            {
            lv_onlineStore_11_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_onlineStore_11_0, grammarAccess.getFeatureStoreSpecAccess().getOnlineStoreSTRINGTerminalRuleCall_11_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getFeatureStoreSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"onlineStore",
            						lv_onlineStore_11_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_12=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_12, grammarAccess.getFeatureStoreSpecAccess().getRightCurlyBracketKeyword_12());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleFeatureStoreSpec"


    // $ANTLR start "entryRuleSecretRef"
    // InternalMlopsDsl.g:6107:1: entryRuleSecretRef returns [EObject current=null] : iv_ruleSecretRef= ruleSecretRef EOF ;
    public final EObject entryRuleSecretRef() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSecretRef = null;


        try {
            // InternalMlopsDsl.g:6107:50: (iv_ruleSecretRef= ruleSecretRef EOF )
            // InternalMlopsDsl.g:6108:2: iv_ruleSecretRef= ruleSecretRef EOF
            {
             newCompositeNode(grammarAccess.getSecretRefRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSecretRef=ruleSecretRef();

            state._fsp--;

             current =iv_ruleSecretRef; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSecretRef"


    // $ANTLR start "ruleSecretRef"
    // InternalMlopsDsl.g:6114:1: ruleSecretRef returns [EObject current=null] : (otherlv_0= 'SecretRef' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' ) ;
    public final EObject ruleSecretRef() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_name_4_0=null;
        Token otherlv_5=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:6120:2: ( (otherlv_0= 'SecretRef' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' ) )
            // InternalMlopsDsl.g:6121:2: (otherlv_0= 'SecretRef' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' )
            {
            // InternalMlopsDsl.g:6121:2: (otherlv_0= 'SecretRef' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}' )
            // InternalMlopsDsl.g:6122:3: otherlv_0= 'SecretRef' otherlv_1= '{' otherlv_2= 'name' otherlv_3= '=' ( (lv_name_4_0= RULE_STRING ) ) otherlv_5= '}'
            {
            otherlv_0=(Token)match(input,178,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getSecretRefAccess().getSecretRefKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_5); 

            			newLeafNode(otherlv_1, grammarAccess.getSecretRefAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getSecretRefAccess().getNameKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getSecretRefAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:6138:3: ( (lv_name_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:6139:4: (lv_name_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:6139:4: (lv_name_4_0= RULE_STRING )
            // InternalMlopsDsl.g:6140:5: lv_name_4_0= RULE_STRING
            {
            lv_name_4_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_name_4_0, grammarAccess.getSecretRefAccess().getNameSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSecretRefRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_5, grammarAccess.getSecretRefAccess().getRightCurlyBracketKeyword_5());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSecretRef"


    // $ANTLR start "entryRuleOperationalPlan"
    // InternalMlopsDsl.g:6164:1: entryRuleOperationalPlan returns [EObject current=null] : iv_ruleOperationalPlan= ruleOperationalPlan EOF ;
    public final EObject entryRuleOperationalPlan() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleOperationalPlan = null;


        try {
            // InternalMlopsDsl.g:6164:56: (iv_ruleOperationalPlan= ruleOperationalPlan EOF )
            // InternalMlopsDsl.g:6165:2: iv_ruleOperationalPlan= ruleOperationalPlan EOF
            {
             newCompositeNode(grammarAccess.getOperationalPlanRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleOperationalPlan=ruleOperationalPlan();

            state._fsp--;

             current =iv_ruleOperationalPlan; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleOperationalPlan"


    // $ANTLR start "ruleOperationalPlan"
    // InternalMlopsDsl.g:6171:1: ruleOperationalPlan returns [EObject current=null] : (otherlv_0= 'OperationalPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'rationale' otherlv_4= '=' ( (lv_rationale_5_0= RULE_STRING ) ) otherlv_6= 'compute' otherlv_7= '=' ( (lv_compute_8_0= ruleComputeProfile ) ) otherlv_9= 'packaging' otherlv_10= '=' ( (lv_packaging_11_0= rulePackaging ) ) otherlv_12= 'trainMode' otherlv_13= '=' ( (lv_trainMode_14_0= ruleTrainMode ) ) otherlv_15= 'servingMode' otherlv_16= '=' ( (lv_servingMode_17_0= ruleServingKind ) ) otherlv_18= 'entryCmd' otherlv_19= '=' ( (lv_entryCmd_20_0= RULE_STRING ) ) (otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']' )? (otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) ) )? (otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']' )? otherlv_38= 'ciTarget' otherlv_39= '=' ( (lv_ciTarget_40_0= ruleCITarget ) ) (otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) ) )? (otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) ) )? (otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']' )? (otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) ) )? (otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) ) )? (otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) ) )? otherlv_63= '}' ) ;
    public final EObject ruleOperationalPlan() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token lv_rationale_5_0=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_entryCmd_20_0=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_29=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_32=null;
        Token otherlv_33=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_38=null;
        Token otherlv_39=null;
        Token otherlv_41=null;
        Token otherlv_42=null;
        Token lv_baseImage_43_0=null;
        Token otherlv_44=null;
        Token otherlv_45=null;
        Token lv_dockerfile_46_0=null;
        Token otherlv_47=null;
        Token otherlv_48=null;
        Token otherlv_49=null;
        Token lv_extraDeps_50_0=null;
        Token otherlv_51=null;
        Token lv_extraDeps_52_0=null;
        Token otherlv_53=null;
        Token otherlv_54=null;
        Token otherlv_55=null;
        Token lv_retries_56_0=null;
        Token otherlv_57=null;
        Token otherlv_58=null;
        Token lv_cache_59_1=null;
        Token lv_cache_59_2=null;
        Token otherlv_60=null;
        Token otherlv_61=null;
        Token lv_queue_62_0=null;
        Token otherlv_63=null;
        Enumerator lv_compute_8_0 = null;

        Enumerator lv_packaging_11_0 = null;

        Enumerator lv_trainMode_14_0 = null;

        Enumerator lv_servingMode_17_0 = null;

        EObject lv_resources_24_0 = null;

        EObject lv_resources_26_0 = null;

        EObject lv_secrets_34_0 = null;

        EObject lv_secrets_36_0 = null;

        Enumerator lv_ciTarget_40_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:6177:2: ( (otherlv_0= 'OperationalPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'rationale' otherlv_4= '=' ( (lv_rationale_5_0= RULE_STRING ) ) otherlv_6= 'compute' otherlv_7= '=' ( (lv_compute_8_0= ruleComputeProfile ) ) otherlv_9= 'packaging' otherlv_10= '=' ( (lv_packaging_11_0= rulePackaging ) ) otherlv_12= 'trainMode' otherlv_13= '=' ( (lv_trainMode_14_0= ruleTrainMode ) ) otherlv_15= 'servingMode' otherlv_16= '=' ( (lv_servingMode_17_0= ruleServingKind ) ) otherlv_18= 'entryCmd' otherlv_19= '=' ( (lv_entryCmd_20_0= RULE_STRING ) ) (otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']' )? (otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) ) )? (otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']' )? otherlv_38= 'ciTarget' otherlv_39= '=' ( (lv_ciTarget_40_0= ruleCITarget ) ) (otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) ) )? (otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) ) )? (otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']' )? (otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) ) )? (otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) ) )? (otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) ) )? otherlv_63= '}' ) )
            // InternalMlopsDsl.g:6178:2: (otherlv_0= 'OperationalPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'rationale' otherlv_4= '=' ( (lv_rationale_5_0= RULE_STRING ) ) otherlv_6= 'compute' otherlv_7= '=' ( (lv_compute_8_0= ruleComputeProfile ) ) otherlv_9= 'packaging' otherlv_10= '=' ( (lv_packaging_11_0= rulePackaging ) ) otherlv_12= 'trainMode' otherlv_13= '=' ( (lv_trainMode_14_0= ruleTrainMode ) ) otherlv_15= 'servingMode' otherlv_16= '=' ( (lv_servingMode_17_0= ruleServingKind ) ) otherlv_18= 'entryCmd' otherlv_19= '=' ( (lv_entryCmd_20_0= RULE_STRING ) ) (otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']' )? (otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) ) )? (otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']' )? otherlv_38= 'ciTarget' otherlv_39= '=' ( (lv_ciTarget_40_0= ruleCITarget ) ) (otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) ) )? (otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) ) )? (otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']' )? (otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) ) )? (otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) ) )? (otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) ) )? otherlv_63= '}' )
            {
            // InternalMlopsDsl.g:6178:2: (otherlv_0= 'OperationalPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'rationale' otherlv_4= '=' ( (lv_rationale_5_0= RULE_STRING ) ) otherlv_6= 'compute' otherlv_7= '=' ( (lv_compute_8_0= ruleComputeProfile ) ) otherlv_9= 'packaging' otherlv_10= '=' ( (lv_packaging_11_0= rulePackaging ) ) otherlv_12= 'trainMode' otherlv_13= '=' ( (lv_trainMode_14_0= ruleTrainMode ) ) otherlv_15= 'servingMode' otherlv_16= '=' ( (lv_servingMode_17_0= ruleServingKind ) ) otherlv_18= 'entryCmd' otherlv_19= '=' ( (lv_entryCmd_20_0= RULE_STRING ) ) (otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']' )? (otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) ) )? (otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']' )? otherlv_38= 'ciTarget' otherlv_39= '=' ( (lv_ciTarget_40_0= ruleCITarget ) ) (otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) ) )? (otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) ) )? (otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']' )? (otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) ) )? (otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) ) )? (otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) ) )? otherlv_63= '}' )
            // InternalMlopsDsl.g:6179:3: otherlv_0= 'OperationalPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'rationale' otherlv_4= '=' ( (lv_rationale_5_0= RULE_STRING ) ) otherlv_6= 'compute' otherlv_7= '=' ( (lv_compute_8_0= ruleComputeProfile ) ) otherlv_9= 'packaging' otherlv_10= '=' ( (lv_packaging_11_0= rulePackaging ) ) otherlv_12= 'trainMode' otherlv_13= '=' ( (lv_trainMode_14_0= ruleTrainMode ) ) otherlv_15= 'servingMode' otherlv_16= '=' ( (lv_servingMode_17_0= ruleServingKind ) ) otherlv_18= 'entryCmd' otherlv_19= '=' ( (lv_entryCmd_20_0= RULE_STRING ) ) (otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']' )? (otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) ) )? (otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']' )? otherlv_38= 'ciTarget' otherlv_39= '=' ( (lv_ciTarget_40_0= ruleCITarget ) ) (otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) ) )? (otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) ) )? (otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']' )? (otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) ) )? (otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) ) )? (otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) ) )? otherlv_63= '}'
            {
            otherlv_0=(Token)match(input,179,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getOperationalPlanAccess().getOperationalPlanKeyword_0());
            		
            // InternalMlopsDsl.g:6183:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:6184:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:6184:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:6185:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getOperationalPlanAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOperationalPlanRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_170); 

            			newLeafNode(otherlv_2, grammarAccess.getOperationalPlanAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,180,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getOperationalPlanAccess().getRationaleKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_4, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:6213:3: ( (lv_rationale_5_0= RULE_STRING ) )
            // InternalMlopsDsl.g:6214:4: (lv_rationale_5_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:6214:4: (lv_rationale_5_0= RULE_STRING )
            // InternalMlopsDsl.g:6215:5: lv_rationale_5_0= RULE_STRING
            {
            lv_rationale_5_0=(Token)match(input,RULE_STRING,FOLLOW_171); 

            					newLeafNode(lv_rationale_5_0, grammarAccess.getOperationalPlanAccess().getRationaleSTRINGTerminalRuleCall_5_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOperationalPlanRule());
            					}
            					setWithLastConsumed(
            						current,
            						"rationale",
            						lv_rationale_5_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_6=(Token)match(input,181,FOLLOW_6); 

            			newLeafNode(otherlv_6, grammarAccess.getOperationalPlanAccess().getComputeKeyword_6());
            		
            otherlv_7=(Token)match(input,15,FOLLOW_172); 

            			newLeafNode(otherlv_7, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_7());
            		
            // InternalMlopsDsl.g:6239:3: ( (lv_compute_8_0= ruleComputeProfile ) )
            // InternalMlopsDsl.g:6240:4: (lv_compute_8_0= ruleComputeProfile )
            {
            // InternalMlopsDsl.g:6240:4: (lv_compute_8_0= ruleComputeProfile )
            // InternalMlopsDsl.g:6241:5: lv_compute_8_0= ruleComputeProfile
            {

            					newCompositeNode(grammarAccess.getOperationalPlanAccess().getComputeComputeProfileEnumRuleCall_8_0());
            				
            pushFollow(FOLLOW_173);
            lv_compute_8_0=ruleComputeProfile();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
            					}
            					set(
            						current,
            						"compute",
            						lv_compute_8_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ComputeProfile");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_9=(Token)match(input,182,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getOperationalPlanAccess().getPackagingKeyword_9());
            		
            otherlv_10=(Token)match(input,15,FOLLOW_174); 

            			newLeafNode(otherlv_10, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_10());
            		
            // InternalMlopsDsl.g:6266:3: ( (lv_packaging_11_0= rulePackaging ) )
            // InternalMlopsDsl.g:6267:4: (lv_packaging_11_0= rulePackaging )
            {
            // InternalMlopsDsl.g:6267:4: (lv_packaging_11_0= rulePackaging )
            // InternalMlopsDsl.g:6268:5: lv_packaging_11_0= rulePackaging
            {

            					newCompositeNode(grammarAccess.getOperationalPlanAccess().getPackagingPackagingEnumRuleCall_11_0());
            				
            pushFollow(FOLLOW_175);
            lv_packaging_11_0=rulePackaging();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
            					}
            					set(
            						current,
            						"packaging",
            						lv_packaging_11_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.Packaging");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_12=(Token)match(input,183,FOLLOW_6); 

            			newLeafNode(otherlv_12, grammarAccess.getOperationalPlanAccess().getTrainModeKeyword_12());
            		
            otherlv_13=(Token)match(input,15,FOLLOW_176); 

            			newLeafNode(otherlv_13, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_13());
            		
            // InternalMlopsDsl.g:6293:3: ( (lv_trainMode_14_0= ruleTrainMode ) )
            // InternalMlopsDsl.g:6294:4: (lv_trainMode_14_0= ruleTrainMode )
            {
            // InternalMlopsDsl.g:6294:4: (lv_trainMode_14_0= ruleTrainMode )
            // InternalMlopsDsl.g:6295:5: lv_trainMode_14_0= ruleTrainMode
            {

            					newCompositeNode(grammarAccess.getOperationalPlanAccess().getTrainModeTrainModeEnumRuleCall_14_0());
            				
            pushFollow(FOLLOW_177);
            lv_trainMode_14_0=ruleTrainMode();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
            					}
            					set(
            						current,
            						"trainMode",
            						lv_trainMode_14_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.TrainMode");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_15=(Token)match(input,184,FOLLOW_6); 

            			newLeafNode(otherlv_15, grammarAccess.getOperationalPlanAccess().getServingModeKeyword_15());
            		
            otherlv_16=(Token)match(input,15,FOLLOW_93); 

            			newLeafNode(otherlv_16, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_16());
            		
            // InternalMlopsDsl.g:6320:3: ( (lv_servingMode_17_0= ruleServingKind ) )
            // InternalMlopsDsl.g:6321:4: (lv_servingMode_17_0= ruleServingKind )
            {
            // InternalMlopsDsl.g:6321:4: (lv_servingMode_17_0= ruleServingKind )
            // InternalMlopsDsl.g:6322:5: lv_servingMode_17_0= ruleServingKind
            {

            					newCompositeNode(grammarAccess.getOperationalPlanAccess().getServingModeServingKindEnumRuleCall_17_0());
            				
            pushFollow(FOLLOW_178);
            lv_servingMode_17_0=ruleServingKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
            					}
            					set(
            						current,
            						"servingMode",
            						lv_servingMode_17_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ServingKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_18=(Token)match(input,185,FOLLOW_6); 

            			newLeafNode(otherlv_18, grammarAccess.getOperationalPlanAccess().getEntryCmdKeyword_18());
            		
            otherlv_19=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_19, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_19());
            		
            // InternalMlopsDsl.g:6347:3: ( (lv_entryCmd_20_0= RULE_STRING ) )
            // InternalMlopsDsl.g:6348:4: (lv_entryCmd_20_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:6348:4: (lv_entryCmd_20_0= RULE_STRING )
            // InternalMlopsDsl.g:6349:5: lv_entryCmd_20_0= RULE_STRING
            {
            lv_entryCmd_20_0=(Token)match(input,RULE_STRING,FOLLOW_179); 

            					newLeafNode(lv_entryCmd_20_0, grammarAccess.getOperationalPlanAccess().getEntryCmdSTRINGTerminalRuleCall_20_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getOperationalPlanRule());
            					}
            					setWithLastConsumed(
            						current,
            						"entryCmd",
            						lv_entryCmd_20_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:6365:3: (otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']' )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==87) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // InternalMlopsDsl.g:6366:4: otherlv_21= 'resources' otherlv_22= '=' otherlv_23= '[' ( (lv_resources_24_0= ruleResource ) ) (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )* otherlv_27= ']'
                    {
                    otherlv_21=(Token)match(input,87,FOLLOW_6); 

                    				newLeafNode(otherlv_21, grammarAccess.getOperationalPlanAccess().getResourcesKeyword_21_0());
                    			
                    otherlv_22=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_22, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_21_1());
                    			
                    otherlv_23=(Token)match(input,35,FOLLOW_80); 

                    				newLeafNode(otherlv_23, grammarAccess.getOperationalPlanAccess().getLeftSquareBracketKeyword_21_2());
                    			
                    // InternalMlopsDsl.g:6378:4: ( (lv_resources_24_0= ruleResource ) )
                    // InternalMlopsDsl.g:6379:5: (lv_resources_24_0= ruleResource )
                    {
                    // InternalMlopsDsl.g:6379:5: (lv_resources_24_0= ruleResource )
                    // InternalMlopsDsl.g:6380:6: lv_resources_24_0= ruleResource
                    {

                    						newCompositeNode(grammarAccess.getOperationalPlanAccess().getResourcesResourceParserRuleCall_21_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_resources_24_0=ruleResource();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
                    						}
                    						add(
                    							current,
                    							"resources",
                    							lv_resources_24_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Resource");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:6397:4: (otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) ) )*
                    loop106:
                    do {
                        int alt106=2;
                        int LA106_0 = input.LA(1);

                        if ( (LA106_0==36) ) {
                            alt106=1;
                        }


                        switch (alt106) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:6398:5: otherlv_25= ',' ( (lv_resources_26_0= ruleResource ) )
                    	    {
                    	    otherlv_25=(Token)match(input,36,FOLLOW_80); 

                    	    					newLeafNode(otherlv_25, grammarAccess.getOperationalPlanAccess().getCommaKeyword_21_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:6402:5: ( (lv_resources_26_0= ruleResource ) )
                    	    // InternalMlopsDsl.g:6403:6: (lv_resources_26_0= ruleResource )
                    	    {
                    	    // InternalMlopsDsl.g:6403:6: (lv_resources_26_0= ruleResource )
                    	    // InternalMlopsDsl.g:6404:7: lv_resources_26_0= ruleResource
                    	    {

                    	    							newCompositeNode(grammarAccess.getOperationalPlanAccess().getResourcesResourceParserRuleCall_21_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_resources_26_0=ruleResource();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"resources",
                    	    								lv_resources_26_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Resource");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop106;
                        }
                    } while (true);

                    otherlv_27=(Token)match(input,37,FOLLOW_180); 

                    				newLeafNode(otherlv_27, grammarAccess.getOperationalPlanAccess().getRightSquareBracketKeyword_21_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:6427:3: (otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) ) )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==186) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // InternalMlopsDsl.g:6428:4: otherlv_28= 'featureStore' otherlv_29= '=' ( (otherlv_30= RULE_ID ) )
                    {
                    otherlv_28=(Token)match(input,186,FOLLOW_6); 

                    				newLeafNode(otherlv_28, grammarAccess.getOperationalPlanAccess().getFeatureStoreKeyword_22_0());
                    			
                    otherlv_29=(Token)match(input,15,FOLLOW_32); 

                    				newLeafNode(otherlv_29, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_22_1());
                    			
                    // InternalMlopsDsl.g:6436:4: ( (otherlv_30= RULE_ID ) )
                    // InternalMlopsDsl.g:6437:5: (otherlv_30= RULE_ID )
                    {
                    // InternalMlopsDsl.g:6437:5: (otherlv_30= RULE_ID )
                    // InternalMlopsDsl.g:6438:6: otherlv_30= RULE_ID
                    {

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOperationalPlanRule());
                    						}
                    					
                    otherlv_30=(Token)match(input,RULE_ID,FOLLOW_181); 

                    						newLeafNode(otherlv_30, grammarAccess.getOperationalPlanAccess().getFeatureStoreFeatureStoreSpecCrossReference_22_2_0());
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6450:3: (otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']' )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==187) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // InternalMlopsDsl.g:6451:4: otherlv_31= 'secrets' otherlv_32= '=' otherlv_33= '[' ( (lv_secrets_34_0= ruleSecretRef ) ) (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )* otherlv_37= ']'
                    {
                    otherlv_31=(Token)match(input,187,FOLLOW_6); 

                    				newLeafNode(otherlv_31, grammarAccess.getOperationalPlanAccess().getSecretsKeyword_23_0());
                    			
                    otherlv_32=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_32, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_23_1());
                    			
                    otherlv_33=(Token)match(input,35,FOLLOW_182); 

                    				newLeafNode(otherlv_33, grammarAccess.getOperationalPlanAccess().getLeftSquareBracketKeyword_23_2());
                    			
                    // InternalMlopsDsl.g:6463:4: ( (lv_secrets_34_0= ruleSecretRef ) )
                    // InternalMlopsDsl.g:6464:5: (lv_secrets_34_0= ruleSecretRef )
                    {
                    // InternalMlopsDsl.g:6464:5: (lv_secrets_34_0= ruleSecretRef )
                    // InternalMlopsDsl.g:6465:6: lv_secrets_34_0= ruleSecretRef
                    {

                    						newCompositeNode(grammarAccess.getOperationalPlanAccess().getSecretsSecretRefParserRuleCall_23_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_secrets_34_0=ruleSecretRef();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
                    						}
                    						add(
                    							current,
                    							"secrets",
                    							lv_secrets_34_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.SecretRef");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:6482:4: (otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) ) )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==36) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:6483:5: otherlv_35= ',' ( (lv_secrets_36_0= ruleSecretRef ) )
                    	    {
                    	    otherlv_35=(Token)match(input,36,FOLLOW_182); 

                    	    					newLeafNode(otherlv_35, grammarAccess.getOperationalPlanAccess().getCommaKeyword_23_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:6487:5: ( (lv_secrets_36_0= ruleSecretRef ) )
                    	    // InternalMlopsDsl.g:6488:6: (lv_secrets_36_0= ruleSecretRef )
                    	    {
                    	    // InternalMlopsDsl.g:6488:6: (lv_secrets_36_0= ruleSecretRef )
                    	    // InternalMlopsDsl.g:6489:7: lv_secrets_36_0= ruleSecretRef
                    	    {

                    	    							newCompositeNode(grammarAccess.getOperationalPlanAccess().getSecretsSecretRefParserRuleCall_23_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_secrets_36_0=ruleSecretRef();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"secrets",
                    	    								lv_secrets_36_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.SecretRef");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);

                    otherlv_37=(Token)match(input,37,FOLLOW_183); 

                    				newLeafNode(otherlv_37, grammarAccess.getOperationalPlanAccess().getRightSquareBracketKeyword_23_5());
                    			

                    }
                    break;

            }

            otherlv_38=(Token)match(input,188,FOLLOW_6); 

            			newLeafNode(otherlv_38, grammarAccess.getOperationalPlanAccess().getCiTargetKeyword_24());
            		
            otherlv_39=(Token)match(input,15,FOLLOW_160); 

            			newLeafNode(otherlv_39, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_25());
            		
            // InternalMlopsDsl.g:6520:3: ( (lv_ciTarget_40_0= ruleCITarget ) )
            // InternalMlopsDsl.g:6521:4: (lv_ciTarget_40_0= ruleCITarget )
            {
            // InternalMlopsDsl.g:6521:4: (lv_ciTarget_40_0= ruleCITarget )
            // InternalMlopsDsl.g:6522:5: lv_ciTarget_40_0= ruleCITarget
            {

            					newCompositeNode(grammarAccess.getOperationalPlanAccess().getCiTargetCITargetEnumRuleCall_26_0());
            				
            pushFollow(FOLLOW_184);
            lv_ciTarget_40_0=ruleCITarget();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getOperationalPlanRule());
            					}
            					set(
            						current,
            						"ciTarget",
            						lv_ciTarget_40_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.CITarget");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:6539:3: (otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) ) )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==189) ) {
                alt111=1;
            }
            switch (alt111) {
                case 1 :
                    // InternalMlopsDsl.g:6540:4: otherlv_41= 'baseImage' otherlv_42= '=' ( (lv_baseImage_43_0= RULE_STRING ) )
                    {
                    otherlv_41=(Token)match(input,189,FOLLOW_6); 

                    				newLeafNode(otherlv_41, grammarAccess.getOperationalPlanAccess().getBaseImageKeyword_27_0());
                    			
                    otherlv_42=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_42, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_27_1());
                    			
                    // InternalMlopsDsl.g:6548:4: ( (lv_baseImage_43_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6549:5: (lv_baseImage_43_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6549:5: (lv_baseImage_43_0= RULE_STRING )
                    // InternalMlopsDsl.g:6550:6: lv_baseImage_43_0= RULE_STRING
                    {
                    lv_baseImage_43_0=(Token)match(input,RULE_STRING,FOLLOW_185); 

                    						newLeafNode(lv_baseImage_43_0, grammarAccess.getOperationalPlanAccess().getBaseImageSTRINGTerminalRuleCall_27_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOperationalPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"baseImage",
                    							lv_baseImage_43_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6567:3: (otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) ) )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==190) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // InternalMlopsDsl.g:6568:4: otherlv_44= 'dockerfile' otherlv_45= '=' ( (lv_dockerfile_46_0= RULE_STRING ) )
                    {
                    otherlv_44=(Token)match(input,190,FOLLOW_6); 

                    				newLeafNode(otherlv_44, grammarAccess.getOperationalPlanAccess().getDockerfileKeyword_28_0());
                    			
                    otherlv_45=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_45, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_28_1());
                    			
                    // InternalMlopsDsl.g:6576:4: ( (lv_dockerfile_46_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6577:5: (lv_dockerfile_46_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6577:5: (lv_dockerfile_46_0= RULE_STRING )
                    // InternalMlopsDsl.g:6578:6: lv_dockerfile_46_0= RULE_STRING
                    {
                    lv_dockerfile_46_0=(Token)match(input,RULE_STRING,FOLLOW_186); 

                    						newLeafNode(lv_dockerfile_46_0, grammarAccess.getOperationalPlanAccess().getDockerfileSTRINGTerminalRuleCall_28_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOperationalPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"dockerfile",
                    							lv_dockerfile_46_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6595:3: (otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']' )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==191) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // InternalMlopsDsl.g:6596:4: otherlv_47= 'extraDeps' otherlv_48= '=' otherlv_49= '[' ( (lv_extraDeps_50_0= RULE_STRING ) ) (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )* otherlv_53= ']'
                    {
                    otherlv_47=(Token)match(input,191,FOLLOW_6); 

                    				newLeafNode(otherlv_47, grammarAccess.getOperationalPlanAccess().getExtraDepsKeyword_29_0());
                    			
                    otherlv_48=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_48, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_29_1());
                    			
                    otherlv_49=(Token)match(input,35,FOLLOW_7); 

                    				newLeafNode(otherlv_49, grammarAccess.getOperationalPlanAccess().getLeftSquareBracketKeyword_29_2());
                    			
                    // InternalMlopsDsl.g:6608:4: ( (lv_extraDeps_50_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6609:5: (lv_extraDeps_50_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6609:5: (lv_extraDeps_50_0= RULE_STRING )
                    // InternalMlopsDsl.g:6610:6: lv_extraDeps_50_0= RULE_STRING
                    {
                    lv_extraDeps_50_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    						newLeafNode(lv_extraDeps_50_0, grammarAccess.getOperationalPlanAccess().getExtraDepsSTRINGTerminalRuleCall_29_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOperationalPlanRule());
                    						}
                    						addWithLastConsumed(
                    							current,
                    							"extraDeps",
                    							lv_extraDeps_50_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }

                    // InternalMlopsDsl.g:6626:4: (otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) ) )*
                    loop113:
                    do {
                        int alt113=2;
                        int LA113_0 = input.LA(1);

                        if ( (LA113_0==36) ) {
                            alt113=1;
                        }


                        switch (alt113) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:6627:5: otherlv_51= ',' ( (lv_extraDeps_52_0= RULE_STRING ) )
                    	    {
                    	    otherlv_51=(Token)match(input,36,FOLLOW_7); 

                    	    					newLeafNode(otherlv_51, grammarAccess.getOperationalPlanAccess().getCommaKeyword_29_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:6631:5: ( (lv_extraDeps_52_0= RULE_STRING ) )
                    	    // InternalMlopsDsl.g:6632:6: (lv_extraDeps_52_0= RULE_STRING )
                    	    {
                    	    // InternalMlopsDsl.g:6632:6: (lv_extraDeps_52_0= RULE_STRING )
                    	    // InternalMlopsDsl.g:6633:7: lv_extraDeps_52_0= RULE_STRING
                    	    {
                    	    lv_extraDeps_52_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    	    							newLeafNode(lv_extraDeps_52_0, grammarAccess.getOperationalPlanAccess().getExtraDepsSTRINGTerminalRuleCall_29_4_1_0());
                    	    						

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getOperationalPlanRule());
                    	    							}
                    	    							addWithLastConsumed(
                    	    								current,
                    	    								"extraDeps",
                    	    								lv_extraDeps_52_0,
                    	    								"org.eclipse.xtext.common.Terminals.STRING");
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop113;
                        }
                    } while (true);

                    otherlv_53=(Token)match(input,37,FOLLOW_187); 

                    				newLeafNode(otherlv_53, grammarAccess.getOperationalPlanAccess().getRightSquareBracketKeyword_29_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:6655:3: (otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) ) )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==192) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // InternalMlopsDsl.g:6656:4: otherlv_54= 'retries' otherlv_55= '=' ( (lv_retries_56_0= RULE_INT ) )
                    {
                    otherlv_54=(Token)match(input,192,FOLLOW_6); 

                    				newLeafNode(otherlv_54, grammarAccess.getOperationalPlanAccess().getRetriesKeyword_30_0());
                    			
                    otherlv_55=(Token)match(input,15,FOLLOW_17); 

                    				newLeafNode(otherlv_55, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_30_1());
                    			
                    // InternalMlopsDsl.g:6664:4: ( (lv_retries_56_0= RULE_INT ) )
                    // InternalMlopsDsl.g:6665:5: (lv_retries_56_0= RULE_INT )
                    {
                    // InternalMlopsDsl.g:6665:5: (lv_retries_56_0= RULE_INT )
                    // InternalMlopsDsl.g:6666:6: lv_retries_56_0= RULE_INT
                    {
                    lv_retries_56_0=(Token)match(input,RULE_INT,FOLLOW_188); 

                    						newLeafNode(lv_retries_56_0, grammarAccess.getOperationalPlanAccess().getRetriesINTTerminalRuleCall_30_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOperationalPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"retries",
                    							lv_retries_56_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6683:3: (otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) ) )?
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==193) ) {
                alt117=1;
            }
            switch (alt117) {
                case 1 :
                    // InternalMlopsDsl.g:6684:4: otherlv_57= 'cache' otherlv_58= '=' ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) )
                    {
                    otherlv_57=(Token)match(input,193,FOLLOW_6); 

                    				newLeafNode(otherlv_57, grammarAccess.getOperationalPlanAccess().getCacheKeyword_31_0());
                    			
                    otherlv_58=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_58, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_31_1());
                    			
                    // InternalMlopsDsl.g:6692:4: ( ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) ) )
                    // InternalMlopsDsl.g:6693:5: ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:6693:5: ( (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' ) )
                    // InternalMlopsDsl.g:6694:6: (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' )
                    {
                    // InternalMlopsDsl.g:6694:6: (lv_cache_59_1= 'true' | lv_cache_59_2= 'false' )
                    int alt116=2;
                    int LA116_0 = input.LA(1);

                    if ( (LA116_0==31) ) {
                        alt116=1;
                    }
                    else if ( (LA116_0==32) ) {
                        alt116=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 116, 0, input);

                        throw nvae;
                    }
                    switch (alt116) {
                        case 1 :
                            // InternalMlopsDsl.g:6695:7: lv_cache_59_1= 'true'
                            {
                            lv_cache_59_1=(Token)match(input,31,FOLLOW_189); 

                            							newLeafNode(lv_cache_59_1, grammarAccess.getOperationalPlanAccess().getCacheTrueKeyword_31_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getOperationalPlanRule());
                            							}
                            							setWithLastConsumed(current, "cache", lv_cache_59_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:6706:7: lv_cache_59_2= 'false'
                            {
                            lv_cache_59_2=(Token)match(input,32,FOLLOW_189); 

                            							newLeafNode(lv_cache_59_2, grammarAccess.getOperationalPlanAccess().getCacheFalseKeyword_31_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getOperationalPlanRule());
                            							}
                            							setWithLastConsumed(current, "cache", lv_cache_59_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6720:3: (otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) ) )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==194) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // InternalMlopsDsl.g:6721:4: otherlv_60= 'queue' otherlv_61= '=' ( (lv_queue_62_0= RULE_STRING ) )
                    {
                    otherlv_60=(Token)match(input,194,FOLLOW_6); 

                    				newLeafNode(otherlv_60, grammarAccess.getOperationalPlanAccess().getQueueKeyword_32_0());
                    			
                    otherlv_61=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_61, grammarAccess.getOperationalPlanAccess().getEqualsSignKeyword_32_1());
                    			
                    // InternalMlopsDsl.g:6729:4: ( (lv_queue_62_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6730:5: (lv_queue_62_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6730:5: (lv_queue_62_0= RULE_STRING )
                    // InternalMlopsDsl.g:6731:6: lv_queue_62_0= RULE_STRING
                    {
                    lv_queue_62_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

                    						newLeafNode(lv_queue_62_0, grammarAccess.getOperationalPlanAccess().getQueueSTRINGTerminalRuleCall_32_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getOperationalPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"queue",
                    							lv_queue_62_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_63=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_63, grammarAccess.getOperationalPlanAccess().getRightCurlyBracketKeyword_33());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOperationalPlan"


    // $ANTLR start "entryRuleNetworkSpec"
    // InternalMlopsDsl.g:6756:1: entryRuleNetworkSpec returns [EObject current=null] : iv_ruleNetworkSpec= ruleNetworkSpec EOF ;
    public final EObject entryRuleNetworkSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleNetworkSpec = null;


        try {
            // InternalMlopsDsl.g:6756:52: (iv_ruleNetworkSpec= ruleNetworkSpec EOF )
            // InternalMlopsDsl.g:6757:2: iv_ruleNetworkSpec= ruleNetworkSpec EOF
            {
             newCompositeNode(grammarAccess.getNetworkSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleNetworkSpec=ruleNetworkSpec();

            state._fsp--;

             current =iv_ruleNetworkSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleNetworkSpec"


    // $ANTLR start "ruleNetworkSpec"
    // InternalMlopsDsl.g:6763:1: ruleNetworkSpec returns [EObject current=null] : (otherlv_0= 'NetworkSpec' otherlv_1= '{' otherlv_2= 'exposure' otherlv_3= '=' ( (lv_exposure_4_0= ruleExposureKind ) ) (otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) ) )? (otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) ) )? (otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) ) )? (otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) ) )? (otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) ) )? (otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']' )? otherlv_27= '}' ) ;
    public final EObject ruleNetworkSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_domain_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_tls_10_1=null;
        Token lv_tls_10_2=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_ingressClass_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_vpcId_16_0=null;
        Token otherlv_17=null;
        Token otherlv_18=null;
        Token lv_cidr_19_0=null;
        Token otherlv_20=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token lv_subnets_23_0=null;
        Token otherlv_24=null;
        Token lv_subnets_25_0=null;
        Token otherlv_26=null;
        Token otherlv_27=null;
        Enumerator lv_exposure_4_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:6769:2: ( (otherlv_0= 'NetworkSpec' otherlv_1= '{' otherlv_2= 'exposure' otherlv_3= '=' ( (lv_exposure_4_0= ruleExposureKind ) ) (otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) ) )? (otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) ) )? (otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) ) )? (otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) ) )? (otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) ) )? (otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']' )? otherlv_27= '}' ) )
            // InternalMlopsDsl.g:6770:2: (otherlv_0= 'NetworkSpec' otherlv_1= '{' otherlv_2= 'exposure' otherlv_3= '=' ( (lv_exposure_4_0= ruleExposureKind ) ) (otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) ) )? (otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) ) )? (otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) ) )? (otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) ) )? (otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) ) )? (otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']' )? otherlv_27= '}' )
            {
            // InternalMlopsDsl.g:6770:2: (otherlv_0= 'NetworkSpec' otherlv_1= '{' otherlv_2= 'exposure' otherlv_3= '=' ( (lv_exposure_4_0= ruleExposureKind ) ) (otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) ) )? (otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) ) )? (otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) ) )? (otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) ) )? (otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) ) )? (otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']' )? otherlv_27= '}' )
            // InternalMlopsDsl.g:6771:3: otherlv_0= 'NetworkSpec' otherlv_1= '{' otherlv_2= 'exposure' otherlv_3= '=' ( (lv_exposure_4_0= ruleExposureKind ) ) (otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) ) )? (otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) ) )? (otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) ) )? (otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) ) )? (otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) ) )? (otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']' )? otherlv_27= '}'
            {
            otherlv_0=(Token)match(input,195,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getNetworkSpecAccess().getNetworkSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_190); 

            			newLeafNode(otherlv_1, grammarAccess.getNetworkSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,196,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getNetworkSpecAccess().getExposureKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_191); 

            			newLeafNode(otherlv_3, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:6787:3: ( (lv_exposure_4_0= ruleExposureKind ) )
            // InternalMlopsDsl.g:6788:4: (lv_exposure_4_0= ruleExposureKind )
            {
            // InternalMlopsDsl.g:6788:4: (lv_exposure_4_0= ruleExposureKind )
            // InternalMlopsDsl.g:6789:5: lv_exposure_4_0= ruleExposureKind
            {

            					newCompositeNode(grammarAccess.getNetworkSpecAccess().getExposureExposureKindEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_192);
            lv_exposure_4_0=ruleExposureKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getNetworkSpecRule());
            					}
            					set(
            						current,
            						"exposure",
            						lv_exposure_4_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ExposureKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:6806:3: (otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) ) )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==197) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // InternalMlopsDsl.g:6807:4: otherlv_5= 'domain' otherlv_6= '=' ( (lv_domain_7_0= RULE_STRING ) )
                    {
                    otherlv_5=(Token)match(input,197,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getNetworkSpecAccess().getDomainKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_6, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:6815:4: ( (lv_domain_7_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6816:5: (lv_domain_7_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6816:5: (lv_domain_7_0= RULE_STRING )
                    // InternalMlopsDsl.g:6817:6: lv_domain_7_0= RULE_STRING
                    {
                    lv_domain_7_0=(Token)match(input,RULE_STRING,FOLLOW_193); 

                    						newLeafNode(lv_domain_7_0, grammarAccess.getNetworkSpecAccess().getDomainSTRINGTerminalRuleCall_5_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNetworkSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"domain",
                    							lv_domain_7_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6834:3: (otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) ) )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==198) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // InternalMlopsDsl.g:6835:4: otherlv_8= 'tls' otherlv_9= '=' ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) )
                    {
                    otherlv_8=(Token)match(input,198,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getNetworkSpecAccess().getTlsKeyword_6_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_9, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:6843:4: ( ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) ) )
                    // InternalMlopsDsl.g:6844:5: ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:6844:5: ( (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' ) )
                    // InternalMlopsDsl.g:6845:6: (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' )
                    {
                    // InternalMlopsDsl.g:6845:6: (lv_tls_10_1= 'true' | lv_tls_10_2= 'false' )
                    int alt120=2;
                    int LA120_0 = input.LA(1);

                    if ( (LA120_0==31) ) {
                        alt120=1;
                    }
                    else if ( (LA120_0==32) ) {
                        alt120=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 120, 0, input);

                        throw nvae;
                    }
                    switch (alt120) {
                        case 1 :
                            // InternalMlopsDsl.g:6846:7: lv_tls_10_1= 'true'
                            {
                            lv_tls_10_1=(Token)match(input,31,FOLLOW_194); 

                            							newLeafNode(lv_tls_10_1, grammarAccess.getNetworkSpecAccess().getTlsTrueKeyword_6_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getNetworkSpecRule());
                            							}
                            							setWithLastConsumed(current, "tls", lv_tls_10_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:6857:7: lv_tls_10_2= 'false'
                            {
                            lv_tls_10_2=(Token)match(input,32,FOLLOW_194); 

                            							newLeafNode(lv_tls_10_2, grammarAccess.getNetworkSpecAccess().getTlsFalseKeyword_6_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getNetworkSpecRule());
                            							}
                            							setWithLastConsumed(current, "tls", lv_tls_10_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6871:3: (otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) ) )?
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==199) ) {
                alt122=1;
            }
            switch (alt122) {
                case 1 :
                    // InternalMlopsDsl.g:6872:4: otherlv_11= 'ingressClass' otherlv_12= '=' ( (lv_ingressClass_13_0= RULE_STRING ) )
                    {
                    otherlv_11=(Token)match(input,199,FOLLOW_6); 

                    				newLeafNode(otherlv_11, grammarAccess.getNetworkSpecAccess().getIngressClassKeyword_7_0());
                    			
                    otherlv_12=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_12, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:6880:4: ( (lv_ingressClass_13_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6881:5: (lv_ingressClass_13_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6881:5: (lv_ingressClass_13_0= RULE_STRING )
                    // InternalMlopsDsl.g:6882:6: lv_ingressClass_13_0= RULE_STRING
                    {
                    lv_ingressClass_13_0=(Token)match(input,RULE_STRING,FOLLOW_195); 

                    						newLeafNode(lv_ingressClass_13_0, grammarAccess.getNetworkSpecAccess().getIngressClassSTRINGTerminalRuleCall_7_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNetworkSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"ingressClass",
                    							lv_ingressClass_13_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6899:3: (otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) ) )?
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( (LA123_0==200) ) {
                alt123=1;
            }
            switch (alt123) {
                case 1 :
                    // InternalMlopsDsl.g:6900:4: otherlv_14= 'vpcId' otherlv_15= '=' ( (lv_vpcId_16_0= RULE_STRING ) )
                    {
                    otherlv_14=(Token)match(input,200,FOLLOW_6); 

                    				newLeafNode(otherlv_14, grammarAccess.getNetworkSpecAccess().getVpcIdKeyword_8_0());
                    			
                    otherlv_15=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_15, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_8_1());
                    			
                    // InternalMlopsDsl.g:6908:4: ( (lv_vpcId_16_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6909:5: (lv_vpcId_16_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6909:5: (lv_vpcId_16_0= RULE_STRING )
                    // InternalMlopsDsl.g:6910:6: lv_vpcId_16_0= RULE_STRING
                    {
                    lv_vpcId_16_0=(Token)match(input,RULE_STRING,FOLLOW_196); 

                    						newLeafNode(lv_vpcId_16_0, grammarAccess.getNetworkSpecAccess().getVpcIdSTRINGTerminalRuleCall_8_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNetworkSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"vpcId",
                    							lv_vpcId_16_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6927:3: (otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) ) )?
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( (LA124_0==201) ) {
                alt124=1;
            }
            switch (alt124) {
                case 1 :
                    // InternalMlopsDsl.g:6928:4: otherlv_17= 'cidr' otherlv_18= '=' ( (lv_cidr_19_0= RULE_STRING ) )
                    {
                    otherlv_17=(Token)match(input,201,FOLLOW_6); 

                    				newLeafNode(otherlv_17, grammarAccess.getNetworkSpecAccess().getCidrKeyword_9_0());
                    			
                    otherlv_18=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_18, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_9_1());
                    			
                    // InternalMlopsDsl.g:6936:4: ( (lv_cidr_19_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6937:5: (lv_cidr_19_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6937:5: (lv_cidr_19_0= RULE_STRING )
                    // InternalMlopsDsl.g:6938:6: lv_cidr_19_0= RULE_STRING
                    {
                    lv_cidr_19_0=(Token)match(input,RULE_STRING,FOLLOW_197); 

                    						newLeafNode(lv_cidr_19_0, grammarAccess.getNetworkSpecAccess().getCidrSTRINGTerminalRuleCall_9_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNetworkSpecRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"cidr",
                    							lv_cidr_19_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:6955:3: (otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']' )?
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==202) ) {
                alt126=1;
            }
            switch (alt126) {
                case 1 :
                    // InternalMlopsDsl.g:6956:4: otherlv_20= 'subnets' otherlv_21= '=' otherlv_22= '[' ( (lv_subnets_23_0= RULE_STRING ) ) (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )* otherlv_26= ']'
                    {
                    otherlv_20=(Token)match(input,202,FOLLOW_6); 

                    				newLeafNode(otherlv_20, grammarAccess.getNetworkSpecAccess().getSubnetsKeyword_10_0());
                    			
                    otherlv_21=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_21, grammarAccess.getNetworkSpecAccess().getEqualsSignKeyword_10_1());
                    			
                    otherlv_22=(Token)match(input,35,FOLLOW_7); 

                    				newLeafNode(otherlv_22, grammarAccess.getNetworkSpecAccess().getLeftSquareBracketKeyword_10_2());
                    			
                    // InternalMlopsDsl.g:6968:4: ( (lv_subnets_23_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:6969:5: (lv_subnets_23_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:6969:5: (lv_subnets_23_0= RULE_STRING )
                    // InternalMlopsDsl.g:6970:6: lv_subnets_23_0= RULE_STRING
                    {
                    lv_subnets_23_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    						newLeafNode(lv_subnets_23_0, grammarAccess.getNetworkSpecAccess().getSubnetsSTRINGTerminalRuleCall_10_3_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getNetworkSpecRule());
                    						}
                    						addWithLastConsumed(
                    							current,
                    							"subnets",
                    							lv_subnets_23_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }

                    // InternalMlopsDsl.g:6986:4: (otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) ) )*
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( (LA125_0==36) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:6987:5: otherlv_24= ',' ( (lv_subnets_25_0= RULE_STRING ) )
                    	    {
                    	    otherlv_24=(Token)match(input,36,FOLLOW_7); 

                    	    					newLeafNode(otherlv_24, grammarAccess.getNetworkSpecAccess().getCommaKeyword_10_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:6991:5: ( (lv_subnets_25_0= RULE_STRING ) )
                    	    // InternalMlopsDsl.g:6992:6: (lv_subnets_25_0= RULE_STRING )
                    	    {
                    	    // InternalMlopsDsl.g:6992:6: (lv_subnets_25_0= RULE_STRING )
                    	    // InternalMlopsDsl.g:6993:7: lv_subnets_25_0= RULE_STRING
                    	    {
                    	    lv_subnets_25_0=(Token)match(input,RULE_STRING,FOLLOW_26); 

                    	    							newLeafNode(lv_subnets_25_0, grammarAccess.getNetworkSpecAccess().getSubnetsSTRINGTerminalRuleCall_10_4_1_0());
                    	    						

                    	    							if (current==null) {
                    	    								current = createModelElement(grammarAccess.getNetworkSpecRule());
                    	    							}
                    	    							addWithLastConsumed(
                    	    								current,
                    	    								"subnets",
                    	    								lv_subnets_25_0,
                    	    								"org.eclipse.xtext.common.Terminals.STRING");
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop125;
                        }
                    } while (true);

                    otherlv_26=(Token)match(input,37,FOLLOW_8); 

                    				newLeafNode(otherlv_26, grammarAccess.getNetworkSpecAccess().getRightSquareBracketKeyword_10_5());
                    			

                    }
                    break;

            }

            otherlv_27=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_27, grammarAccess.getNetworkSpecAccess().getRightCurlyBracketKeyword_11());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleNetworkSpec"


    // $ANTLR start "entryRuleStorageTarget"
    // InternalMlopsDsl.g:7023:1: entryRuleStorageTarget returns [EObject current=null] : iv_ruleStorageTarget= ruleStorageTarget EOF ;
    public final EObject entryRuleStorageTarget() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleStorageTarget = null;


        try {
            // InternalMlopsDsl.g:7023:54: (iv_ruleStorageTarget= ruleStorageTarget EOF )
            // InternalMlopsDsl.g:7024:2: iv_ruleStorageTarget= ruleStorageTarget EOF
            {
             newCompositeNode(grammarAccess.getStorageTargetRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleStorageTarget=ruleStorageTarget();

            state._fsp--;

             current =iv_ruleStorageTarget; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleStorageTarget"


    // $ANTLR start "ruleStorageTarget"
    // InternalMlopsDsl.g:7030:1: ruleStorageTarget returns [EObject current=null] : (otherlv_0= 'StorageTarget' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleStorageClass ) ) otherlv_5= 'purpose' otherlv_6= '=' ( (lv_purpose_7_0= RULE_STRING ) ) otherlv_8= 'name' otherlv_9= '=' ( (lv_name_10_0= RULE_STRING ) ) (otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) ) )? (otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) ) )? otherlv_17= '}' ) ;
    public final EObject ruleStorageTarget() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_purpose_7_0=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_name_10_0=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_retention_13_0=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token lv_encrypted_16_1=null;
        Token lv_encrypted_16_2=null;
        Token otherlv_17=null;
        Enumerator lv_kind_4_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:7036:2: ( (otherlv_0= 'StorageTarget' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleStorageClass ) ) otherlv_5= 'purpose' otherlv_6= '=' ( (lv_purpose_7_0= RULE_STRING ) ) otherlv_8= 'name' otherlv_9= '=' ( (lv_name_10_0= RULE_STRING ) ) (otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) ) )? (otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) ) )? otherlv_17= '}' ) )
            // InternalMlopsDsl.g:7037:2: (otherlv_0= 'StorageTarget' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleStorageClass ) ) otherlv_5= 'purpose' otherlv_6= '=' ( (lv_purpose_7_0= RULE_STRING ) ) otherlv_8= 'name' otherlv_9= '=' ( (lv_name_10_0= RULE_STRING ) ) (otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) ) )? (otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) ) )? otherlv_17= '}' )
            {
            // InternalMlopsDsl.g:7037:2: (otherlv_0= 'StorageTarget' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleStorageClass ) ) otherlv_5= 'purpose' otherlv_6= '=' ( (lv_purpose_7_0= RULE_STRING ) ) otherlv_8= 'name' otherlv_9= '=' ( (lv_name_10_0= RULE_STRING ) ) (otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) ) )? (otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) ) )? otherlv_17= '}' )
            // InternalMlopsDsl.g:7038:3: otherlv_0= 'StorageTarget' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleStorageClass ) ) otherlv_5= 'purpose' otherlv_6= '=' ( (lv_purpose_7_0= RULE_STRING ) ) otherlv_8= 'name' otherlv_9= '=' ( (lv_name_10_0= RULE_STRING ) ) (otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) ) )? (otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) ) )? otherlv_17= '}'
            {
            otherlv_0=(Token)match(input,203,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getStorageTargetAccess().getStorageTargetKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getStorageTargetAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getStorageTargetAccess().getKindKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_198); 

            			newLeafNode(otherlv_3, grammarAccess.getStorageTargetAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:7054:3: ( (lv_kind_4_0= ruleStorageClass ) )
            // InternalMlopsDsl.g:7055:4: (lv_kind_4_0= ruleStorageClass )
            {
            // InternalMlopsDsl.g:7055:4: (lv_kind_4_0= ruleStorageClass )
            // InternalMlopsDsl.g:7056:5: lv_kind_4_0= ruleStorageClass
            {

            					newCompositeNode(grammarAccess.getStorageTargetAccess().getKindStorageClassEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_199);
            lv_kind_4_0=ruleStorageClass();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getStorageTargetRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_4_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.StorageClass");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,204,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getStorageTargetAccess().getPurposeKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_6, grammarAccess.getStorageTargetAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:7081:3: ( (lv_purpose_7_0= RULE_STRING ) )
            // InternalMlopsDsl.g:7082:4: (lv_purpose_7_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:7082:4: (lv_purpose_7_0= RULE_STRING )
            // InternalMlopsDsl.g:7083:5: lv_purpose_7_0= RULE_STRING
            {
            lv_purpose_7_0=(Token)match(input,RULE_STRING,FOLLOW_5); 

            					newLeafNode(lv_purpose_7_0, grammarAccess.getStorageTargetAccess().getPurposeSTRINGTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStorageTargetRule());
            					}
            					setWithLastConsumed(
            						current,
            						"purpose",
            						lv_purpose_7_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_8=(Token)match(input,14,FOLLOW_6); 

            			newLeafNode(otherlv_8, grammarAccess.getStorageTargetAccess().getNameKeyword_8());
            		
            otherlv_9=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_9, grammarAccess.getStorageTargetAccess().getEqualsSignKeyword_9());
            		
            // InternalMlopsDsl.g:7107:3: ( (lv_name_10_0= RULE_STRING ) )
            // InternalMlopsDsl.g:7108:4: (lv_name_10_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:7108:4: (lv_name_10_0= RULE_STRING )
            // InternalMlopsDsl.g:7109:5: lv_name_10_0= RULE_STRING
            {
            lv_name_10_0=(Token)match(input,RULE_STRING,FOLLOW_200); 

            					newLeafNode(lv_name_10_0, grammarAccess.getStorageTargetAccess().getNameSTRINGTerminalRuleCall_10_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getStorageTargetRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_10_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            // InternalMlopsDsl.g:7125:3: (otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) ) )?
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==205) ) {
                alt127=1;
            }
            switch (alt127) {
                case 1 :
                    // InternalMlopsDsl.g:7126:4: otherlv_11= 'retention' otherlv_12= '=' ( (lv_retention_13_0= RULE_STRING ) )
                    {
                    otherlv_11=(Token)match(input,205,FOLLOW_6); 

                    				newLeafNode(otherlv_11, grammarAccess.getStorageTargetAccess().getRetentionKeyword_11_0());
                    			
                    otherlv_12=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_12, grammarAccess.getStorageTargetAccess().getEqualsSignKeyword_11_1());
                    			
                    // InternalMlopsDsl.g:7134:4: ( (lv_retention_13_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:7135:5: (lv_retention_13_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:7135:5: (lv_retention_13_0= RULE_STRING )
                    // InternalMlopsDsl.g:7136:6: lv_retention_13_0= RULE_STRING
                    {
                    lv_retention_13_0=(Token)match(input,RULE_STRING,FOLLOW_201); 

                    						newLeafNode(lv_retention_13_0, grammarAccess.getStorageTargetAccess().getRetentionSTRINGTerminalRuleCall_11_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getStorageTargetRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"retention",
                    							lv_retention_13_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7153:3: (otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) ) )?
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( (LA129_0==206) ) {
                alt129=1;
            }
            switch (alt129) {
                case 1 :
                    // InternalMlopsDsl.g:7154:4: otherlv_14= 'encrypted' otherlv_15= '=' ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) )
                    {
                    otherlv_14=(Token)match(input,206,FOLLOW_6); 

                    				newLeafNode(otherlv_14, grammarAccess.getStorageTargetAccess().getEncryptedKeyword_12_0());
                    			
                    otherlv_15=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_15, grammarAccess.getStorageTargetAccess().getEqualsSignKeyword_12_1());
                    			
                    // InternalMlopsDsl.g:7162:4: ( ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7163:5: ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7163:5: ( (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' ) )
                    // InternalMlopsDsl.g:7164:6: (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7164:6: (lv_encrypted_16_1= 'true' | lv_encrypted_16_2= 'false' )
                    int alt128=2;
                    int LA128_0 = input.LA(1);

                    if ( (LA128_0==31) ) {
                        alt128=1;
                    }
                    else if ( (LA128_0==32) ) {
                        alt128=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 128, 0, input);

                        throw nvae;
                    }
                    switch (alt128) {
                        case 1 :
                            // InternalMlopsDsl.g:7165:7: lv_encrypted_16_1= 'true'
                            {
                            lv_encrypted_16_1=(Token)match(input,31,FOLLOW_8); 

                            							newLeafNode(lv_encrypted_16_1, grammarAccess.getStorageTargetAccess().getEncryptedTrueKeyword_12_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getStorageTargetRule());
                            							}
                            							setWithLastConsumed(current, "encrypted", lv_encrypted_16_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:7176:7: lv_encrypted_16_2= 'false'
                            {
                            lv_encrypted_16_2=(Token)match(input,32,FOLLOW_8); 

                            							newLeafNode(lv_encrypted_16_2, grammarAccess.getStorageTargetAccess().getEncryptedFalseKeyword_12_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getStorageTargetRule());
                            							}
                            							setWithLastConsumed(current, "encrypted", lv_encrypted_16_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            otherlv_17=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_17, grammarAccess.getStorageTargetAccess().getRightCurlyBracketKeyword_13());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStorageTarget"


    // $ANTLR start "entryRuleSecretBackendSpec"
    // InternalMlopsDsl.g:7198:1: entryRuleSecretBackendSpec returns [EObject current=null] : iv_ruleSecretBackendSpec= ruleSecretBackendSpec EOF ;
    public final EObject entryRuleSecretBackendSpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleSecretBackendSpec = null;


        try {
            // InternalMlopsDsl.g:7198:58: (iv_ruleSecretBackendSpec= ruleSecretBackendSpec EOF )
            // InternalMlopsDsl.g:7199:2: iv_ruleSecretBackendSpec= ruleSecretBackendSpec EOF
            {
             newCompositeNode(grammarAccess.getSecretBackendSpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSecretBackendSpec=ruleSecretBackendSpec();

            state._fsp--;

             current =iv_ruleSecretBackendSpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSecretBackendSpec"


    // $ANTLR start "ruleSecretBackendSpec"
    // InternalMlopsDsl.g:7205:1: ruleSecretBackendSpec returns [EObject current=null] : (otherlv_0= 'SecretBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleSecretBackend ) ) otherlv_5= 'pathPrefix' otherlv_6= '=' ( (lv_pathPrefix_7_0= RULE_STRING ) ) otherlv_8= '}' ) ;
    public final EObject ruleSecretBackendSpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_pathPrefix_7_0=null;
        Token otherlv_8=null;
        Enumerator lv_kind_4_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:7211:2: ( (otherlv_0= 'SecretBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleSecretBackend ) ) otherlv_5= 'pathPrefix' otherlv_6= '=' ( (lv_pathPrefix_7_0= RULE_STRING ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:7212:2: (otherlv_0= 'SecretBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleSecretBackend ) ) otherlv_5= 'pathPrefix' otherlv_6= '=' ( (lv_pathPrefix_7_0= RULE_STRING ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:7212:2: (otherlv_0= 'SecretBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleSecretBackend ) ) otherlv_5= 'pathPrefix' otherlv_6= '=' ( (lv_pathPrefix_7_0= RULE_STRING ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:7213:3: otherlv_0= 'SecretBackendSpec' otherlv_1= '{' otherlv_2= 'kind' otherlv_3= '=' ( (lv_kind_4_0= ruleSecretBackend ) ) otherlv_5= 'pathPrefix' otherlv_6= '=' ( (lv_pathPrefix_7_0= RULE_STRING ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,207,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getSecretBackendSpecAccess().getSecretBackendSpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_9); 

            			newLeafNode(otherlv_1, grammarAccess.getSecretBackendSpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getSecretBackendSpecAccess().getKindKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_202); 

            			newLeafNode(otherlv_3, grammarAccess.getSecretBackendSpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:7229:3: ( (lv_kind_4_0= ruleSecretBackend ) )
            // InternalMlopsDsl.g:7230:4: (lv_kind_4_0= ruleSecretBackend )
            {
            // InternalMlopsDsl.g:7230:4: (lv_kind_4_0= ruleSecretBackend )
            // InternalMlopsDsl.g:7231:5: lv_kind_4_0= ruleSecretBackend
            {

            					newCompositeNode(grammarAccess.getSecretBackendSpecAccess().getKindSecretBackendEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_203);
            lv_kind_4_0=ruleSecretBackend();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getSecretBackendSpecRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_4_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.SecretBackend");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_5=(Token)match(input,208,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getSecretBackendSpecAccess().getPathPrefixKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_6, grammarAccess.getSecretBackendSpecAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:7256:3: ( (lv_pathPrefix_7_0= RULE_STRING ) )
            // InternalMlopsDsl.g:7257:4: (lv_pathPrefix_7_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:7257:4: (lv_pathPrefix_7_0= RULE_STRING )
            // InternalMlopsDsl.g:7258:5: lv_pathPrefix_7_0= RULE_STRING
            {
            lv_pathPrefix_7_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_pathPrefix_7_0, grammarAccess.getSecretBackendSpecAccess().getPathPrefixSTRINGTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getSecretBackendSpecRule());
            					}
            					setWithLastConsumed(
            						current,
            						"pathPrefix",
            						lv_pathPrefix_7_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getSecretBackendSpecAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSecretBackendSpec"


    // $ANTLR start "entryRuleObservabilitySpec"
    // InternalMlopsDsl.g:7282:1: entryRuleObservabilitySpec returns [EObject current=null] : iv_ruleObservabilitySpec= ruleObservabilitySpec EOF ;
    public final EObject entryRuleObservabilitySpec() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleObservabilitySpec = null;


        try {
            // InternalMlopsDsl.g:7282:58: (iv_ruleObservabilitySpec= ruleObservabilitySpec EOF )
            // InternalMlopsDsl.g:7283:2: iv_ruleObservabilitySpec= ruleObservabilitySpec EOF
            {
             newCompositeNode(grammarAccess.getObservabilitySpecRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleObservabilitySpec=ruleObservabilitySpec();

            state._fsp--;

             current =iv_ruleObservabilitySpec; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleObservabilitySpec"


    // $ANTLR start "ruleObservabilitySpec"
    // InternalMlopsDsl.g:7289:1: ruleObservabilitySpec returns [EObject current=null] : (otherlv_0= 'ObservabilitySpec' otherlv_1= '{' otherlv_2= 'stack' otherlv_3= '=' ( (lv_stack_4_0= ruleObservabilityStack ) ) (otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) ) )? (otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) ) )? (otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) ) )? otherlv_14= '}' ) ;
    public final EObject ruleObservabilitySpec() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_logsEnabled_7_1=null;
        Token lv_logsEnabled_7_2=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token lv_metricsEnabled_10_1=null;
        Token lv_metricsEnabled_10_2=null;
        Token otherlv_11=null;
        Token otherlv_12=null;
        Token lv_tracesEnabled_13_1=null;
        Token lv_tracesEnabled_13_2=null;
        Token otherlv_14=null;
        Enumerator lv_stack_4_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:7295:2: ( (otherlv_0= 'ObservabilitySpec' otherlv_1= '{' otherlv_2= 'stack' otherlv_3= '=' ( (lv_stack_4_0= ruleObservabilityStack ) ) (otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) ) )? (otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) ) )? (otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) ) )? otherlv_14= '}' ) )
            // InternalMlopsDsl.g:7296:2: (otherlv_0= 'ObservabilitySpec' otherlv_1= '{' otherlv_2= 'stack' otherlv_3= '=' ( (lv_stack_4_0= ruleObservabilityStack ) ) (otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) ) )? (otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) ) )? (otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) ) )? otherlv_14= '}' )
            {
            // InternalMlopsDsl.g:7296:2: (otherlv_0= 'ObservabilitySpec' otherlv_1= '{' otherlv_2= 'stack' otherlv_3= '=' ( (lv_stack_4_0= ruleObservabilityStack ) ) (otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) ) )? (otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) ) )? (otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) ) )? otherlv_14= '}' )
            // InternalMlopsDsl.g:7297:3: otherlv_0= 'ObservabilitySpec' otherlv_1= '{' otherlv_2= 'stack' otherlv_3= '=' ( (lv_stack_4_0= ruleObservabilityStack ) ) (otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) ) )? (otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) ) )? (otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) ) )? otherlv_14= '}'
            {
            otherlv_0=(Token)match(input,209,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getObservabilitySpecAccess().getObservabilitySpecKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_204); 

            			newLeafNode(otherlv_1, grammarAccess.getObservabilitySpecAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,210,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getObservabilitySpecAccess().getStackKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_205); 

            			newLeafNode(otherlv_3, grammarAccess.getObservabilitySpecAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:7313:3: ( (lv_stack_4_0= ruleObservabilityStack ) )
            // InternalMlopsDsl.g:7314:4: (lv_stack_4_0= ruleObservabilityStack )
            {
            // InternalMlopsDsl.g:7314:4: (lv_stack_4_0= ruleObservabilityStack )
            // InternalMlopsDsl.g:7315:5: lv_stack_4_0= ruleObservabilityStack
            {

            					newCompositeNode(grammarAccess.getObservabilitySpecAccess().getStackObservabilityStackEnumRuleCall_4_0());
            				
            pushFollow(FOLLOW_206);
            lv_stack_4_0=ruleObservabilityStack();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getObservabilitySpecRule());
            					}
            					set(
            						current,
            						"stack",
            						lv_stack_4_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ObservabilityStack");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:7332:3: (otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) ) )?
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( (LA131_0==211) ) {
                alt131=1;
            }
            switch (alt131) {
                case 1 :
                    // InternalMlopsDsl.g:7333:4: otherlv_5= 'logsEnabled' otherlv_6= '=' ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) )
                    {
                    otherlv_5=(Token)match(input,211,FOLLOW_6); 

                    				newLeafNode(otherlv_5, grammarAccess.getObservabilitySpecAccess().getLogsEnabledKeyword_5_0());
                    			
                    otherlv_6=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_6, grammarAccess.getObservabilitySpecAccess().getEqualsSignKeyword_5_1());
                    			
                    // InternalMlopsDsl.g:7341:4: ( ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7342:5: ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7342:5: ( (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' ) )
                    // InternalMlopsDsl.g:7343:6: (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7343:6: (lv_logsEnabled_7_1= 'true' | lv_logsEnabled_7_2= 'false' )
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( (LA130_0==31) ) {
                        alt130=1;
                    }
                    else if ( (LA130_0==32) ) {
                        alt130=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 130, 0, input);

                        throw nvae;
                    }
                    switch (alt130) {
                        case 1 :
                            // InternalMlopsDsl.g:7344:7: lv_logsEnabled_7_1= 'true'
                            {
                            lv_logsEnabled_7_1=(Token)match(input,31,FOLLOW_207); 

                            							newLeafNode(lv_logsEnabled_7_1, grammarAccess.getObservabilitySpecAccess().getLogsEnabledTrueKeyword_5_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObservabilitySpecRule());
                            							}
                            							setWithLastConsumed(current, "logsEnabled", lv_logsEnabled_7_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:7355:7: lv_logsEnabled_7_2= 'false'
                            {
                            lv_logsEnabled_7_2=(Token)match(input,32,FOLLOW_207); 

                            							newLeafNode(lv_logsEnabled_7_2, grammarAccess.getObservabilitySpecAccess().getLogsEnabledFalseKeyword_5_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObservabilitySpecRule());
                            							}
                            							setWithLastConsumed(current, "logsEnabled", lv_logsEnabled_7_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7369:3: (otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) ) )?
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==212) ) {
                alt133=1;
            }
            switch (alt133) {
                case 1 :
                    // InternalMlopsDsl.g:7370:4: otherlv_8= 'metricsEnabled' otherlv_9= '=' ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) )
                    {
                    otherlv_8=(Token)match(input,212,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getObservabilitySpecAccess().getMetricsEnabledKeyword_6_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_9, grammarAccess.getObservabilitySpecAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:7378:4: ( ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7379:5: ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7379:5: ( (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' ) )
                    // InternalMlopsDsl.g:7380:6: (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7380:6: (lv_metricsEnabled_10_1= 'true' | lv_metricsEnabled_10_2= 'false' )
                    int alt132=2;
                    int LA132_0 = input.LA(1);

                    if ( (LA132_0==31) ) {
                        alt132=1;
                    }
                    else if ( (LA132_0==32) ) {
                        alt132=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 132, 0, input);

                        throw nvae;
                    }
                    switch (alt132) {
                        case 1 :
                            // InternalMlopsDsl.g:7381:7: lv_metricsEnabled_10_1= 'true'
                            {
                            lv_metricsEnabled_10_1=(Token)match(input,31,FOLLOW_208); 

                            							newLeafNode(lv_metricsEnabled_10_1, grammarAccess.getObservabilitySpecAccess().getMetricsEnabledTrueKeyword_6_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObservabilitySpecRule());
                            							}
                            							setWithLastConsumed(current, "metricsEnabled", lv_metricsEnabled_10_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:7392:7: lv_metricsEnabled_10_2= 'false'
                            {
                            lv_metricsEnabled_10_2=(Token)match(input,32,FOLLOW_208); 

                            							newLeafNode(lv_metricsEnabled_10_2, grammarAccess.getObservabilitySpecAccess().getMetricsEnabledFalseKeyword_6_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObservabilitySpecRule());
                            							}
                            							setWithLastConsumed(current, "metricsEnabled", lv_metricsEnabled_10_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7406:3: (otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) ) )?
            int alt135=2;
            int LA135_0 = input.LA(1);

            if ( (LA135_0==213) ) {
                alt135=1;
            }
            switch (alt135) {
                case 1 :
                    // InternalMlopsDsl.g:7407:4: otherlv_11= 'tracesEnabled' otherlv_12= '=' ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) )
                    {
                    otherlv_11=(Token)match(input,213,FOLLOW_6); 

                    				newLeafNode(otherlv_11, grammarAccess.getObservabilitySpecAccess().getTracesEnabledKeyword_7_0());
                    			
                    otherlv_12=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_12, grammarAccess.getObservabilitySpecAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:7415:4: ( ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7416:5: ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7416:5: ( (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' ) )
                    // InternalMlopsDsl.g:7417:6: (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7417:6: (lv_tracesEnabled_13_1= 'true' | lv_tracesEnabled_13_2= 'false' )
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( (LA134_0==31) ) {
                        alt134=1;
                    }
                    else if ( (LA134_0==32) ) {
                        alt134=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 134, 0, input);

                        throw nvae;
                    }
                    switch (alt134) {
                        case 1 :
                            // InternalMlopsDsl.g:7418:7: lv_tracesEnabled_13_1= 'true'
                            {
                            lv_tracesEnabled_13_1=(Token)match(input,31,FOLLOW_8); 

                            							newLeafNode(lv_tracesEnabled_13_1, grammarAccess.getObservabilitySpecAccess().getTracesEnabledTrueKeyword_7_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObservabilitySpecRule());
                            							}
                            							setWithLastConsumed(current, "tracesEnabled", lv_tracesEnabled_13_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:7429:7: lv_tracesEnabled_13_2= 'false'
                            {
                            lv_tracesEnabled_13_2=(Token)match(input,32,FOLLOW_8); 

                            							newLeafNode(lv_tracesEnabled_13_2, grammarAccess.getObservabilitySpecAccess().getTracesEnabledFalseKeyword_7_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getObservabilitySpecRule());
                            							}
                            							setWithLastConsumed(current, "tracesEnabled", lv_tracesEnabled_13_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            otherlv_14=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_14, grammarAccess.getObservabilitySpecAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObservabilitySpec"


    // $ANTLR start "entryRuleInfraPlan"
    // InternalMlopsDsl.g:7451:1: entryRuleInfraPlan returns [EObject current=null] : iv_ruleInfraPlan= ruleInfraPlan EOF ;
    public final EObject entryRuleInfraPlan() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleInfraPlan = null;


        try {
            // InternalMlopsDsl.g:7451:50: (iv_ruleInfraPlan= ruleInfraPlan EOF )
            // InternalMlopsDsl.g:7452:2: iv_ruleInfraPlan= ruleInfraPlan EOF
            {
             newCompositeNode(grammarAccess.getInfraPlanRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleInfraPlan=ruleInfraPlan();

            state._fsp--;

             current =iv_ruleInfraPlan; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleInfraPlan"


    // $ANTLR start "ruleInfraPlan"
    // InternalMlopsDsl.g:7458:1: ruleInfraPlan returns [EObject current=null] : (otherlv_0= 'InfraPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'provider' otherlv_4= '=' ( (lv_provider_5_0= ruleCloudProvider ) ) (otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) ) )? (otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) ) )? otherlv_12= 'orchestrator' otherlv_13= '=' ( (lv_orchestrator_14_0= ruleOrchestrator ) ) otherlv_15= 'clusterName' otherlv_16= '=' ( (lv_clusterName_17_0= RULE_STRING ) ) otherlv_18= 'namespace' otherlv_19= '=' ( (lv_namespace_20_0= RULE_STRING ) ) otherlv_21= 'environment' otherlv_22= '=' ( (lv_environment_23_0= ruleEnvironmentSpec ) ) otherlv_24= 'backend' otherlv_25= '=' ( (lv_backend_26_0= ruleTerraformBackendSpec ) ) otherlv_27= 'runtime' otherlv_28= '=' ( (lv_runtime_29_0= ruleRuntimeSpec ) ) otherlv_30= 'network' otherlv_31= '=' ( (lv_network_32_0= ruleNetworkSpec ) ) otherlv_33= 'storage' otherlv_34= '=' otherlv_35= '[' ( (lv_storage_36_0= ruleStorageTarget ) ) (otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) ) )* otherlv_39= ']' otherlv_40= 'secrets' otherlv_41= '=' ( (lv_secrets_42_0= ruleSecretBackendSpec ) ) otherlv_43= 'observability' otherlv_44= '=' ( (lv_observability_45_0= ruleObservabilitySpec ) ) (otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) ) )? (otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) ) )? (otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) ) )? (otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) ) )? otherlv_58= '}' ) ;
    public final EObject ruleInfraPlan() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token lv_name_1_0=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token lv_accountId_8_0=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token lv_region_11_0=null;
        Token otherlv_12=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token lv_clusterName_17_0=null;
        Token otherlv_18=null;
        Token otherlv_19=null;
        Token lv_namespace_20_0=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_24=null;
        Token otherlv_25=null;
        Token otherlv_27=null;
        Token otherlv_28=null;
        Token otherlv_30=null;
        Token otherlv_31=null;
        Token otherlv_33=null;
        Token otherlv_34=null;
        Token otherlv_35=null;
        Token otherlv_37=null;
        Token otherlv_39=null;
        Token otherlv_40=null;
        Token otherlv_41=null;
        Token otherlv_43=null;
        Token otherlv_44=null;
        Token otherlv_46=null;
        Token otherlv_47=null;
        Token lv_spotAllowed_48_1=null;
        Token lv_spotAllowed_48_2=null;
        Token otherlv_49=null;
        Token otherlv_50=null;
        Token lv_maxMonthlyBudget_51_0=null;
        Token otherlv_52=null;
        Token otherlv_53=null;
        Token lv_piiData_54_1=null;
        Token lv_piiData_54_2=null;
        Token otherlv_55=null;
        Token otherlv_56=null;
        Token lv_gdprRestricted_57_1=null;
        Token lv_gdprRestricted_57_2=null;
        Token otherlv_58=null;
        Enumerator lv_provider_5_0 = null;

        Enumerator lv_orchestrator_14_0 = null;

        EObject lv_environment_23_0 = null;

        EObject lv_backend_26_0 = null;

        EObject lv_runtime_29_0 = null;

        EObject lv_network_32_0 = null;

        EObject lv_storage_36_0 = null;

        EObject lv_storage_38_0 = null;

        EObject lv_secrets_42_0 = null;

        EObject lv_observability_45_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:7464:2: ( (otherlv_0= 'InfraPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'provider' otherlv_4= '=' ( (lv_provider_5_0= ruleCloudProvider ) ) (otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) ) )? (otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) ) )? otherlv_12= 'orchestrator' otherlv_13= '=' ( (lv_orchestrator_14_0= ruleOrchestrator ) ) otherlv_15= 'clusterName' otherlv_16= '=' ( (lv_clusterName_17_0= RULE_STRING ) ) otherlv_18= 'namespace' otherlv_19= '=' ( (lv_namespace_20_0= RULE_STRING ) ) otherlv_21= 'environment' otherlv_22= '=' ( (lv_environment_23_0= ruleEnvironmentSpec ) ) otherlv_24= 'backend' otherlv_25= '=' ( (lv_backend_26_0= ruleTerraformBackendSpec ) ) otherlv_27= 'runtime' otherlv_28= '=' ( (lv_runtime_29_0= ruleRuntimeSpec ) ) otherlv_30= 'network' otherlv_31= '=' ( (lv_network_32_0= ruleNetworkSpec ) ) otherlv_33= 'storage' otherlv_34= '=' otherlv_35= '[' ( (lv_storage_36_0= ruleStorageTarget ) ) (otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) ) )* otherlv_39= ']' otherlv_40= 'secrets' otherlv_41= '=' ( (lv_secrets_42_0= ruleSecretBackendSpec ) ) otherlv_43= 'observability' otherlv_44= '=' ( (lv_observability_45_0= ruleObservabilitySpec ) ) (otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) ) )? (otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) ) )? (otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) ) )? (otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) ) )? otherlv_58= '}' ) )
            // InternalMlopsDsl.g:7465:2: (otherlv_0= 'InfraPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'provider' otherlv_4= '=' ( (lv_provider_5_0= ruleCloudProvider ) ) (otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) ) )? (otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) ) )? otherlv_12= 'orchestrator' otherlv_13= '=' ( (lv_orchestrator_14_0= ruleOrchestrator ) ) otherlv_15= 'clusterName' otherlv_16= '=' ( (lv_clusterName_17_0= RULE_STRING ) ) otherlv_18= 'namespace' otherlv_19= '=' ( (lv_namespace_20_0= RULE_STRING ) ) otherlv_21= 'environment' otherlv_22= '=' ( (lv_environment_23_0= ruleEnvironmentSpec ) ) otherlv_24= 'backend' otherlv_25= '=' ( (lv_backend_26_0= ruleTerraformBackendSpec ) ) otherlv_27= 'runtime' otherlv_28= '=' ( (lv_runtime_29_0= ruleRuntimeSpec ) ) otherlv_30= 'network' otherlv_31= '=' ( (lv_network_32_0= ruleNetworkSpec ) ) otherlv_33= 'storage' otherlv_34= '=' otherlv_35= '[' ( (lv_storage_36_0= ruleStorageTarget ) ) (otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) ) )* otherlv_39= ']' otherlv_40= 'secrets' otherlv_41= '=' ( (lv_secrets_42_0= ruleSecretBackendSpec ) ) otherlv_43= 'observability' otherlv_44= '=' ( (lv_observability_45_0= ruleObservabilitySpec ) ) (otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) ) )? (otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) ) )? (otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) ) )? (otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) ) )? otherlv_58= '}' )
            {
            // InternalMlopsDsl.g:7465:2: (otherlv_0= 'InfraPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'provider' otherlv_4= '=' ( (lv_provider_5_0= ruleCloudProvider ) ) (otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) ) )? (otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) ) )? otherlv_12= 'orchestrator' otherlv_13= '=' ( (lv_orchestrator_14_0= ruleOrchestrator ) ) otherlv_15= 'clusterName' otherlv_16= '=' ( (lv_clusterName_17_0= RULE_STRING ) ) otherlv_18= 'namespace' otherlv_19= '=' ( (lv_namespace_20_0= RULE_STRING ) ) otherlv_21= 'environment' otherlv_22= '=' ( (lv_environment_23_0= ruleEnvironmentSpec ) ) otherlv_24= 'backend' otherlv_25= '=' ( (lv_backend_26_0= ruleTerraformBackendSpec ) ) otherlv_27= 'runtime' otherlv_28= '=' ( (lv_runtime_29_0= ruleRuntimeSpec ) ) otherlv_30= 'network' otherlv_31= '=' ( (lv_network_32_0= ruleNetworkSpec ) ) otherlv_33= 'storage' otherlv_34= '=' otherlv_35= '[' ( (lv_storage_36_0= ruleStorageTarget ) ) (otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) ) )* otherlv_39= ']' otherlv_40= 'secrets' otherlv_41= '=' ( (lv_secrets_42_0= ruleSecretBackendSpec ) ) otherlv_43= 'observability' otherlv_44= '=' ( (lv_observability_45_0= ruleObservabilitySpec ) ) (otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) ) )? (otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) ) )? (otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) ) )? (otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) ) )? otherlv_58= '}' )
            // InternalMlopsDsl.g:7466:3: otherlv_0= 'InfraPlan' ( (lv_name_1_0= RULE_ID ) ) otherlv_2= '{' otherlv_3= 'provider' otherlv_4= '=' ( (lv_provider_5_0= ruleCloudProvider ) ) (otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) ) )? (otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) ) )? otherlv_12= 'orchestrator' otherlv_13= '=' ( (lv_orchestrator_14_0= ruleOrchestrator ) ) otherlv_15= 'clusterName' otherlv_16= '=' ( (lv_clusterName_17_0= RULE_STRING ) ) otherlv_18= 'namespace' otherlv_19= '=' ( (lv_namespace_20_0= RULE_STRING ) ) otherlv_21= 'environment' otherlv_22= '=' ( (lv_environment_23_0= ruleEnvironmentSpec ) ) otherlv_24= 'backend' otherlv_25= '=' ( (lv_backend_26_0= ruleTerraformBackendSpec ) ) otherlv_27= 'runtime' otherlv_28= '=' ( (lv_runtime_29_0= ruleRuntimeSpec ) ) otherlv_30= 'network' otherlv_31= '=' ( (lv_network_32_0= ruleNetworkSpec ) ) otherlv_33= 'storage' otherlv_34= '=' otherlv_35= '[' ( (lv_storage_36_0= ruleStorageTarget ) ) (otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) ) )* otherlv_39= ']' otherlv_40= 'secrets' otherlv_41= '=' ( (lv_secrets_42_0= ruleSecretBackendSpec ) ) otherlv_43= 'observability' otherlv_44= '=' ( (lv_observability_45_0= ruleObservabilitySpec ) ) (otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) ) )? (otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) ) )? (otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) ) )? (otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) ) )? otherlv_58= '}'
            {
            otherlv_0=(Token)match(input,214,FOLLOW_32); 

            			newLeafNode(otherlv_0, grammarAccess.getInfraPlanAccess().getInfraPlanKeyword_0());
            		
            // InternalMlopsDsl.g:7470:3: ( (lv_name_1_0= RULE_ID ) )
            // InternalMlopsDsl.g:7471:4: (lv_name_1_0= RULE_ID )
            {
            // InternalMlopsDsl.g:7471:4: (lv_name_1_0= RULE_ID )
            // InternalMlopsDsl.g:7472:5: lv_name_1_0= RULE_ID
            {
            lv_name_1_0=(Token)match(input,RULE_ID,FOLLOW_4); 

            					newLeafNode(lv_name_1_0, grammarAccess.getInfraPlanAccess().getNameIDTerminalRuleCall_1_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInfraPlanRule());
            					}
            					setWithLastConsumed(
            						current,
            						"name",
            						lv_name_1_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_2=(Token)match(input,13,FOLLOW_209); 

            			newLeafNode(otherlv_2, grammarAccess.getInfraPlanAccess().getLeftCurlyBracketKeyword_2());
            		
            otherlv_3=(Token)match(input,215,FOLLOW_6); 

            			newLeafNode(otherlv_3, grammarAccess.getInfraPlanAccess().getProviderKeyword_3());
            		
            otherlv_4=(Token)match(input,15,FOLLOW_210); 

            			newLeafNode(otherlv_4, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_4());
            		
            // InternalMlopsDsl.g:7500:3: ( (lv_provider_5_0= ruleCloudProvider ) )
            // InternalMlopsDsl.g:7501:4: (lv_provider_5_0= ruleCloudProvider )
            {
            // InternalMlopsDsl.g:7501:4: (lv_provider_5_0= ruleCloudProvider )
            // InternalMlopsDsl.g:7502:5: lv_provider_5_0= ruleCloudProvider
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getProviderCloudProviderEnumRuleCall_5_0());
            				
            pushFollow(FOLLOW_211);
            lv_provider_5_0=ruleCloudProvider();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"provider",
            						lv_provider_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.CloudProvider");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:7519:3: (otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) ) )?
            int alt136=2;
            int LA136_0 = input.LA(1);

            if ( (LA136_0==216) ) {
                alt136=1;
            }
            switch (alt136) {
                case 1 :
                    // InternalMlopsDsl.g:7520:4: otherlv_6= 'accountId' otherlv_7= '=' ( (lv_accountId_8_0= RULE_STRING ) )
                    {
                    otherlv_6=(Token)match(input,216,FOLLOW_6); 

                    				newLeafNode(otherlv_6, grammarAccess.getInfraPlanAccess().getAccountIdKeyword_6_0());
                    			
                    otherlv_7=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_7, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_6_1());
                    			
                    // InternalMlopsDsl.g:7528:4: ( (lv_accountId_8_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:7529:5: (lv_accountId_8_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:7529:5: (lv_accountId_8_0= RULE_STRING )
                    // InternalMlopsDsl.g:7530:6: lv_accountId_8_0= RULE_STRING
                    {
                    lv_accountId_8_0=(Token)match(input,RULE_STRING,FOLLOW_212); 

                    						newLeafNode(lv_accountId_8_0, grammarAccess.getInfraPlanAccess().getAccountIdSTRINGTerminalRuleCall_6_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInfraPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"accountId",
                    							lv_accountId_8_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7547:3: (otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) ) )?
            int alt137=2;
            int LA137_0 = input.LA(1);

            if ( (LA137_0==217) ) {
                alt137=1;
            }
            switch (alt137) {
                case 1 :
                    // InternalMlopsDsl.g:7548:4: otherlv_9= 'region' otherlv_10= '=' ( (lv_region_11_0= RULE_STRING ) )
                    {
                    otherlv_9=(Token)match(input,217,FOLLOW_6); 

                    				newLeafNode(otherlv_9, grammarAccess.getInfraPlanAccess().getRegionKeyword_7_0());
                    			
                    otherlv_10=(Token)match(input,15,FOLLOW_7); 

                    				newLeafNode(otherlv_10, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_7_1());
                    			
                    // InternalMlopsDsl.g:7556:4: ( (lv_region_11_0= RULE_STRING ) )
                    // InternalMlopsDsl.g:7557:5: (lv_region_11_0= RULE_STRING )
                    {
                    // InternalMlopsDsl.g:7557:5: (lv_region_11_0= RULE_STRING )
                    // InternalMlopsDsl.g:7558:6: lv_region_11_0= RULE_STRING
                    {
                    lv_region_11_0=(Token)match(input,RULE_STRING,FOLLOW_213); 

                    						newLeafNode(lv_region_11_0, grammarAccess.getInfraPlanAccess().getRegionSTRINGTerminalRuleCall_7_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInfraPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"region",
                    							lv_region_11_0,
                    							"org.eclipse.xtext.common.Terminals.STRING");
                    					

                    }


                    }


                    }
                    break;

            }

            otherlv_12=(Token)match(input,218,FOLLOW_6); 

            			newLeafNode(otherlv_12, grammarAccess.getInfraPlanAccess().getOrchestratorKeyword_8());
            		
            otherlv_13=(Token)match(input,15,FOLLOW_214); 

            			newLeafNode(otherlv_13, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_9());
            		
            // InternalMlopsDsl.g:7583:3: ( (lv_orchestrator_14_0= ruleOrchestrator ) )
            // InternalMlopsDsl.g:7584:4: (lv_orchestrator_14_0= ruleOrchestrator )
            {
            // InternalMlopsDsl.g:7584:4: (lv_orchestrator_14_0= ruleOrchestrator )
            // InternalMlopsDsl.g:7585:5: lv_orchestrator_14_0= ruleOrchestrator
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getOrchestratorOrchestratorEnumRuleCall_10_0());
            				
            pushFollow(FOLLOW_215);
            lv_orchestrator_14_0=ruleOrchestrator();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"orchestrator",
            						lv_orchestrator_14_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.Orchestrator");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_15=(Token)match(input,219,FOLLOW_6); 

            			newLeafNode(otherlv_15, grammarAccess.getInfraPlanAccess().getClusterNameKeyword_11());
            		
            otherlv_16=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_16, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_12());
            		
            // InternalMlopsDsl.g:7610:3: ( (lv_clusterName_17_0= RULE_STRING ) )
            // InternalMlopsDsl.g:7611:4: (lv_clusterName_17_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:7611:4: (lv_clusterName_17_0= RULE_STRING )
            // InternalMlopsDsl.g:7612:5: lv_clusterName_17_0= RULE_STRING
            {
            lv_clusterName_17_0=(Token)match(input,RULE_STRING,FOLLOW_216); 

            					newLeafNode(lv_clusterName_17_0, grammarAccess.getInfraPlanAccess().getClusterNameSTRINGTerminalRuleCall_13_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInfraPlanRule());
            					}
            					setWithLastConsumed(
            						current,
            						"clusterName",
            						lv_clusterName_17_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_18=(Token)match(input,220,FOLLOW_6); 

            			newLeafNode(otherlv_18, grammarAccess.getInfraPlanAccess().getNamespaceKeyword_14());
            		
            otherlv_19=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_19, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_15());
            		
            // InternalMlopsDsl.g:7636:3: ( (lv_namespace_20_0= RULE_STRING ) )
            // InternalMlopsDsl.g:7637:4: (lv_namespace_20_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:7637:4: (lv_namespace_20_0= RULE_STRING )
            // InternalMlopsDsl.g:7638:5: lv_namespace_20_0= RULE_STRING
            {
            lv_namespace_20_0=(Token)match(input,RULE_STRING,FOLLOW_217); 

            					newLeafNode(lv_namespace_20_0, grammarAccess.getInfraPlanAccess().getNamespaceSTRINGTerminalRuleCall_16_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getInfraPlanRule());
            					}
            					setWithLastConsumed(
            						current,
            						"namespace",
            						lv_namespace_20_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_21=(Token)match(input,221,FOLLOW_6); 

            			newLeafNode(otherlv_21, grammarAccess.getInfraPlanAccess().getEnvironmentKeyword_17());
            		
            otherlv_22=(Token)match(input,15,FOLLOW_218); 

            			newLeafNode(otherlv_22, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_18());
            		
            // InternalMlopsDsl.g:7662:3: ( (lv_environment_23_0= ruleEnvironmentSpec ) )
            // InternalMlopsDsl.g:7663:4: (lv_environment_23_0= ruleEnvironmentSpec )
            {
            // InternalMlopsDsl.g:7663:4: (lv_environment_23_0= ruleEnvironmentSpec )
            // InternalMlopsDsl.g:7664:5: lv_environment_23_0= ruleEnvironmentSpec
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getEnvironmentEnvironmentSpecParserRuleCall_19_0());
            				
            pushFollow(FOLLOW_110);
            lv_environment_23_0=ruleEnvironmentSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"environment",
            						lv_environment_23_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.EnvironmentSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_24=(Token)match(input,117,FOLLOW_6); 

            			newLeafNode(otherlv_24, grammarAccess.getInfraPlanAccess().getBackendKeyword_20());
            		
            otherlv_25=(Token)match(input,15,FOLLOW_219); 

            			newLeafNode(otherlv_25, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_21());
            		
            // InternalMlopsDsl.g:7689:3: ( (lv_backend_26_0= ruleTerraformBackendSpec ) )
            // InternalMlopsDsl.g:7690:4: (lv_backend_26_0= ruleTerraformBackendSpec )
            {
            // InternalMlopsDsl.g:7690:4: (lv_backend_26_0= ruleTerraformBackendSpec )
            // InternalMlopsDsl.g:7691:5: lv_backend_26_0= ruleTerraformBackendSpec
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getBackendTerraformBackendSpecParserRuleCall_22_0());
            				
            pushFollow(FOLLOW_220);
            lv_backend_26_0=ruleTerraformBackendSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"backend",
            						lv_backend_26_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.TerraformBackendSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_27=(Token)match(input,222,FOLLOW_6); 

            			newLeafNode(otherlv_27, grammarAccess.getInfraPlanAccess().getRuntimeKeyword_23());
            		
            otherlv_28=(Token)match(input,15,FOLLOW_221); 

            			newLeafNode(otherlv_28, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_24());
            		
            // InternalMlopsDsl.g:7716:3: ( (lv_runtime_29_0= ruleRuntimeSpec ) )
            // InternalMlopsDsl.g:7717:4: (lv_runtime_29_0= ruleRuntimeSpec )
            {
            // InternalMlopsDsl.g:7717:4: (lv_runtime_29_0= ruleRuntimeSpec )
            // InternalMlopsDsl.g:7718:5: lv_runtime_29_0= ruleRuntimeSpec
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getRuntimeRuntimeSpecParserRuleCall_25_0());
            				
            pushFollow(FOLLOW_222);
            lv_runtime_29_0=ruleRuntimeSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"runtime",
            						lv_runtime_29_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.RuntimeSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_30=(Token)match(input,223,FOLLOW_6); 

            			newLeafNode(otherlv_30, grammarAccess.getInfraPlanAccess().getNetworkKeyword_26());
            		
            otherlv_31=(Token)match(input,15,FOLLOW_223); 

            			newLeafNode(otherlv_31, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_27());
            		
            // InternalMlopsDsl.g:7743:3: ( (lv_network_32_0= ruleNetworkSpec ) )
            // InternalMlopsDsl.g:7744:4: (lv_network_32_0= ruleNetworkSpec )
            {
            // InternalMlopsDsl.g:7744:4: (lv_network_32_0= ruleNetworkSpec )
            // InternalMlopsDsl.g:7745:5: lv_network_32_0= ruleNetworkSpec
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getNetworkNetworkSpecParserRuleCall_28_0());
            				
            pushFollow(FOLLOW_224);
            lv_network_32_0=ruleNetworkSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"network",
            						lv_network_32_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.NetworkSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_33=(Token)match(input,224,FOLLOW_6); 

            			newLeafNode(otherlv_33, grammarAccess.getInfraPlanAccess().getStorageKeyword_29());
            		
            otherlv_34=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_34, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_30());
            		
            otherlv_35=(Token)match(input,35,FOLLOW_225); 

            			newLeafNode(otherlv_35, grammarAccess.getInfraPlanAccess().getLeftSquareBracketKeyword_31());
            		
            // InternalMlopsDsl.g:7774:3: ( (lv_storage_36_0= ruleStorageTarget ) )
            // InternalMlopsDsl.g:7775:4: (lv_storage_36_0= ruleStorageTarget )
            {
            // InternalMlopsDsl.g:7775:4: (lv_storage_36_0= ruleStorageTarget )
            // InternalMlopsDsl.g:7776:5: lv_storage_36_0= ruleStorageTarget
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getStorageStorageTargetParserRuleCall_32_0());
            				
            pushFollow(FOLLOW_26);
            lv_storage_36_0=ruleStorageTarget();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					add(
            						current,
            						"storage",
            						lv_storage_36_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.StorageTarget");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:7793:3: (otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) ) )*
            loop138:
            do {
                int alt138=2;
                int LA138_0 = input.LA(1);

                if ( (LA138_0==36) ) {
                    alt138=1;
                }


                switch (alt138) {
            	case 1 :
            	    // InternalMlopsDsl.g:7794:4: otherlv_37= ',' ( (lv_storage_38_0= ruleStorageTarget ) )
            	    {
            	    otherlv_37=(Token)match(input,36,FOLLOW_225); 

            	    				newLeafNode(otherlv_37, grammarAccess.getInfraPlanAccess().getCommaKeyword_33_0());
            	    			
            	    // InternalMlopsDsl.g:7798:4: ( (lv_storage_38_0= ruleStorageTarget ) )
            	    // InternalMlopsDsl.g:7799:5: (lv_storage_38_0= ruleStorageTarget )
            	    {
            	    // InternalMlopsDsl.g:7799:5: (lv_storage_38_0= ruleStorageTarget )
            	    // InternalMlopsDsl.g:7800:6: lv_storage_38_0= ruleStorageTarget
            	    {

            	    						newCompositeNode(grammarAccess.getInfraPlanAccess().getStorageStorageTargetParserRuleCall_33_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_storage_38_0=ruleStorageTarget();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            	    						}
            	    						add(
            	    							current,
            	    							"storage",
            	    							lv_storage_38_0,
            	    							"org.xtext.ucd.mlopsdsl.MlopsDsl.StorageTarget");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop138;
                }
            } while (true);

            otherlv_39=(Token)match(input,37,FOLLOW_226); 

            			newLeafNode(otherlv_39, grammarAccess.getInfraPlanAccess().getRightSquareBracketKeyword_34());
            		
            otherlv_40=(Token)match(input,187,FOLLOW_6); 

            			newLeafNode(otherlv_40, grammarAccess.getInfraPlanAccess().getSecretsKeyword_35());
            		
            otherlv_41=(Token)match(input,15,FOLLOW_227); 

            			newLeafNode(otherlv_41, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_36());
            		
            // InternalMlopsDsl.g:7830:3: ( (lv_secrets_42_0= ruleSecretBackendSpec ) )
            // InternalMlopsDsl.g:7831:4: (lv_secrets_42_0= ruleSecretBackendSpec )
            {
            // InternalMlopsDsl.g:7831:4: (lv_secrets_42_0= ruleSecretBackendSpec )
            // InternalMlopsDsl.g:7832:5: lv_secrets_42_0= ruleSecretBackendSpec
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getSecretsSecretBackendSpecParserRuleCall_37_0());
            				
            pushFollow(FOLLOW_228);
            lv_secrets_42_0=ruleSecretBackendSpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"secrets",
            						lv_secrets_42_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.SecretBackendSpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_43=(Token)match(input,225,FOLLOW_6); 

            			newLeafNode(otherlv_43, grammarAccess.getInfraPlanAccess().getObservabilityKeyword_38());
            		
            otherlv_44=(Token)match(input,15,FOLLOW_229); 

            			newLeafNode(otherlv_44, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_39());
            		
            // InternalMlopsDsl.g:7857:3: ( (lv_observability_45_0= ruleObservabilitySpec ) )
            // InternalMlopsDsl.g:7858:4: (lv_observability_45_0= ruleObservabilitySpec )
            {
            // InternalMlopsDsl.g:7858:4: (lv_observability_45_0= ruleObservabilitySpec )
            // InternalMlopsDsl.g:7859:5: lv_observability_45_0= ruleObservabilitySpec
            {

            					newCompositeNode(grammarAccess.getInfraPlanAccess().getObservabilityObservabilitySpecParserRuleCall_40_0());
            				
            pushFollow(FOLLOW_230);
            lv_observability_45_0=ruleObservabilitySpec();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getInfraPlanRule());
            					}
            					set(
            						current,
            						"observability",
            						lv_observability_45_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ObservabilitySpec");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:7876:3: (otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) ) )?
            int alt140=2;
            int LA140_0 = input.LA(1);

            if ( (LA140_0==226) ) {
                alt140=1;
            }
            switch (alt140) {
                case 1 :
                    // InternalMlopsDsl.g:7877:4: otherlv_46= 'spotAllowed' otherlv_47= '=' ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) )
                    {
                    otherlv_46=(Token)match(input,226,FOLLOW_6); 

                    				newLeafNode(otherlv_46, grammarAccess.getInfraPlanAccess().getSpotAllowedKeyword_41_0());
                    			
                    otherlv_47=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_47, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_41_1());
                    			
                    // InternalMlopsDsl.g:7885:4: ( ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7886:5: ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7886:5: ( (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' ) )
                    // InternalMlopsDsl.g:7887:6: (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7887:6: (lv_spotAllowed_48_1= 'true' | lv_spotAllowed_48_2= 'false' )
                    int alt139=2;
                    int LA139_0 = input.LA(1);

                    if ( (LA139_0==31) ) {
                        alt139=1;
                    }
                    else if ( (LA139_0==32) ) {
                        alt139=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 139, 0, input);

                        throw nvae;
                    }
                    switch (alt139) {
                        case 1 :
                            // InternalMlopsDsl.g:7888:7: lv_spotAllowed_48_1= 'true'
                            {
                            lv_spotAllowed_48_1=(Token)match(input,31,FOLLOW_231); 

                            							newLeafNode(lv_spotAllowed_48_1, grammarAccess.getInfraPlanAccess().getSpotAllowedTrueKeyword_41_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInfraPlanRule());
                            							}
                            							setWithLastConsumed(current, "spotAllowed", lv_spotAllowed_48_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:7899:7: lv_spotAllowed_48_2= 'false'
                            {
                            lv_spotAllowed_48_2=(Token)match(input,32,FOLLOW_231); 

                            							newLeafNode(lv_spotAllowed_48_2, grammarAccess.getInfraPlanAccess().getSpotAllowedFalseKeyword_41_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInfraPlanRule());
                            							}
                            							setWithLastConsumed(current, "spotAllowed", lv_spotAllowed_48_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7913:3: (otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) ) )?
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==227) ) {
                alt141=1;
            }
            switch (alt141) {
                case 1 :
                    // InternalMlopsDsl.g:7914:4: otherlv_49= 'maxMonthlyBudget' otherlv_50= '=' ( (lv_maxMonthlyBudget_51_0= RULE_INT ) )
                    {
                    otherlv_49=(Token)match(input,227,FOLLOW_6); 

                    				newLeafNode(otherlv_49, grammarAccess.getInfraPlanAccess().getMaxMonthlyBudgetKeyword_42_0());
                    			
                    otherlv_50=(Token)match(input,15,FOLLOW_17); 

                    				newLeafNode(otherlv_50, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_42_1());
                    			
                    // InternalMlopsDsl.g:7922:4: ( (lv_maxMonthlyBudget_51_0= RULE_INT ) )
                    // InternalMlopsDsl.g:7923:5: (lv_maxMonthlyBudget_51_0= RULE_INT )
                    {
                    // InternalMlopsDsl.g:7923:5: (lv_maxMonthlyBudget_51_0= RULE_INT )
                    // InternalMlopsDsl.g:7924:6: lv_maxMonthlyBudget_51_0= RULE_INT
                    {
                    lv_maxMonthlyBudget_51_0=(Token)match(input,RULE_INT,FOLLOW_232); 

                    						newLeafNode(lv_maxMonthlyBudget_51_0, grammarAccess.getInfraPlanAccess().getMaxMonthlyBudgetINTTerminalRuleCall_42_2_0());
                    					

                    						if (current==null) {
                    							current = createModelElement(grammarAccess.getInfraPlanRule());
                    						}
                    						setWithLastConsumed(
                    							current,
                    							"maxMonthlyBudget",
                    							lv_maxMonthlyBudget_51_0,
                    							"org.eclipse.xtext.common.Terminals.INT");
                    					

                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7941:3: (otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) ) )?
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( (LA143_0==228) ) {
                alt143=1;
            }
            switch (alt143) {
                case 1 :
                    // InternalMlopsDsl.g:7942:4: otherlv_52= 'piiData' otherlv_53= '=' ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) )
                    {
                    otherlv_52=(Token)match(input,228,FOLLOW_6); 

                    				newLeafNode(otherlv_52, grammarAccess.getInfraPlanAccess().getPiiDataKeyword_43_0());
                    			
                    otherlv_53=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_53, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_43_1());
                    			
                    // InternalMlopsDsl.g:7950:4: ( ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7951:5: ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7951:5: ( (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' ) )
                    // InternalMlopsDsl.g:7952:6: (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7952:6: (lv_piiData_54_1= 'true' | lv_piiData_54_2= 'false' )
                    int alt142=2;
                    int LA142_0 = input.LA(1);

                    if ( (LA142_0==31) ) {
                        alt142=1;
                    }
                    else if ( (LA142_0==32) ) {
                        alt142=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 142, 0, input);

                        throw nvae;
                    }
                    switch (alt142) {
                        case 1 :
                            // InternalMlopsDsl.g:7953:7: lv_piiData_54_1= 'true'
                            {
                            lv_piiData_54_1=(Token)match(input,31,FOLLOW_233); 

                            							newLeafNode(lv_piiData_54_1, grammarAccess.getInfraPlanAccess().getPiiDataTrueKeyword_43_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInfraPlanRule());
                            							}
                            							setWithLastConsumed(current, "piiData", lv_piiData_54_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:7964:7: lv_piiData_54_2= 'false'
                            {
                            lv_piiData_54_2=(Token)match(input,32,FOLLOW_233); 

                            							newLeafNode(lv_piiData_54_2, grammarAccess.getInfraPlanAccess().getPiiDataFalseKeyword_43_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInfraPlanRule());
                            							}
                            							setWithLastConsumed(current, "piiData", lv_piiData_54_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            // InternalMlopsDsl.g:7978:3: (otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) ) )?
            int alt145=2;
            int LA145_0 = input.LA(1);

            if ( (LA145_0==229) ) {
                alt145=1;
            }
            switch (alt145) {
                case 1 :
                    // InternalMlopsDsl.g:7979:4: otherlv_55= 'gdprRestricted' otherlv_56= '=' ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) )
                    {
                    otherlv_55=(Token)match(input,229,FOLLOW_6); 

                    				newLeafNode(otherlv_55, grammarAccess.getInfraPlanAccess().getGdprRestrictedKeyword_44_0());
                    			
                    otherlv_56=(Token)match(input,15,FOLLOW_21); 

                    				newLeafNode(otherlv_56, grammarAccess.getInfraPlanAccess().getEqualsSignKeyword_44_1());
                    			
                    // InternalMlopsDsl.g:7987:4: ( ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) ) )
                    // InternalMlopsDsl.g:7988:5: ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) )
                    {
                    // InternalMlopsDsl.g:7988:5: ( (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' ) )
                    // InternalMlopsDsl.g:7989:6: (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' )
                    {
                    // InternalMlopsDsl.g:7989:6: (lv_gdprRestricted_57_1= 'true' | lv_gdprRestricted_57_2= 'false' )
                    int alt144=2;
                    int LA144_0 = input.LA(1);

                    if ( (LA144_0==31) ) {
                        alt144=1;
                    }
                    else if ( (LA144_0==32) ) {
                        alt144=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 144, 0, input);

                        throw nvae;
                    }
                    switch (alt144) {
                        case 1 :
                            // InternalMlopsDsl.g:7990:7: lv_gdprRestricted_57_1= 'true'
                            {
                            lv_gdprRestricted_57_1=(Token)match(input,31,FOLLOW_8); 

                            							newLeafNode(lv_gdprRestricted_57_1, grammarAccess.getInfraPlanAccess().getGdprRestrictedTrueKeyword_44_2_0_0());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInfraPlanRule());
                            							}
                            							setWithLastConsumed(current, "gdprRestricted", lv_gdprRestricted_57_1 != null, null);
                            						

                            }
                            break;
                        case 2 :
                            // InternalMlopsDsl.g:8001:7: lv_gdprRestricted_57_2= 'false'
                            {
                            lv_gdprRestricted_57_2=(Token)match(input,32,FOLLOW_8); 

                            							newLeafNode(lv_gdprRestricted_57_2, grammarAccess.getInfraPlanAccess().getGdprRestrictedFalseKeyword_44_2_0_1());
                            						

                            							if (current==null) {
                            								current = createModelElement(grammarAccess.getInfraPlanRule());
                            							}
                            							setWithLastConsumed(current, "gdprRestricted", lv_gdprRestricted_57_2 != null, null);
                            						

                            }
                            break;

                    }


                    }


                    }


                    }
                    break;

            }

            otherlv_58=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_58, grammarAccess.getInfraPlanAccess().getRightCurlyBracketKeyword_45());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleInfraPlan"


    // $ANTLR start "entryRuleParam"
    // InternalMlopsDsl.g:8023:1: entryRuleParam returns [EObject current=null] : iv_ruleParam= ruleParam EOF ;
    public final EObject entryRuleParam() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleParam = null;


        try {
            // InternalMlopsDsl.g:8023:46: (iv_ruleParam= ruleParam EOF )
            // InternalMlopsDsl.g:8024:2: iv_ruleParam= ruleParam EOF
            {
             newCompositeNode(grammarAccess.getParamRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleParam=ruleParam();

            state._fsp--;

             current =iv_ruleParam; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleParam"


    // $ANTLR start "ruleParam"
    // InternalMlopsDsl.g:8030:1: ruleParam returns [EObject current=null] : (otherlv_0= 'Param' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_STRING ) ) otherlv_8= '}' ) ;
    public final EObject ruleParam() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_key_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token lv_value_7_0=null;
        Token otherlv_8=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8036:2: ( (otherlv_0= 'Param' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_STRING ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:8037:2: (otherlv_0= 'Param' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_STRING ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:8037:2: (otherlv_0= 'Param' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_STRING ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:8038:3: otherlv_0= 'Param' otherlv_1= '{' otherlv_2= 'key' otherlv_3= '=' ( (lv_key_4_0= RULE_STRING ) ) otherlv_5= 'value' otherlv_6= '=' ( (lv_value_7_0= RULE_STRING ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,230,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getParamAccess().getParamKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_11); 

            			newLeafNode(otherlv_1, grammarAccess.getParamAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,20,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getParamAccess().getKeyKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getParamAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:8054:3: ( (lv_key_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:8055:4: (lv_key_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:8055:4: (lv_key_4_0= RULE_STRING )
            // InternalMlopsDsl.g:8056:5: lv_key_4_0= RULE_STRING
            {
            lv_key_4_0=(Token)match(input,RULE_STRING,FOLLOW_82); 

            					newLeafNode(lv_key_4_0, grammarAccess.getParamAccess().getKeySTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParamRule());
            					}
            					setWithLastConsumed(
            						current,
            						"key",
            						lv_key_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,90,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getParamAccess().getValueKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_6, grammarAccess.getParamAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:8080:3: ( (lv_value_7_0= RULE_STRING ) )
            // InternalMlopsDsl.g:8081:4: (lv_value_7_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:8081:4: (lv_value_7_0= RULE_STRING )
            // InternalMlopsDsl.g:8082:5: lv_value_7_0= RULE_STRING
            {
            lv_value_7_0=(Token)match(input,RULE_STRING,FOLLOW_8); 

            					newLeafNode(lv_value_7_0, grammarAccess.getParamAccess().getValueSTRINGTerminalRuleCall_7_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getParamRule());
            					}
            					setWithLastConsumed(
            						current,
            						"value",
            						lv_value_7_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getParamAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleParam"


    // $ANTLR start "entryRuleArtifact"
    // InternalMlopsDsl.g:8106:1: entryRuleArtifact returns [EObject current=null] : iv_ruleArtifact= ruleArtifact EOF ;
    public final EObject entryRuleArtifact() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleArtifact = null;


        try {
            // InternalMlopsDsl.g:8106:49: (iv_ruleArtifact= ruleArtifact EOF )
            // InternalMlopsDsl.g:8107:2: iv_ruleArtifact= ruleArtifact EOF
            {
             newCompositeNode(grammarAccess.getArtifactRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleArtifact=ruleArtifact();

            state._fsp--;

             current =iv_ruleArtifact; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleArtifact"


    // $ANTLR start "ruleArtifact"
    // InternalMlopsDsl.g:8113:1: ruleArtifact returns [EObject current=null] : (otherlv_0= 'Artifact' otherlv_1= '{' otherlv_2= 'path' otherlv_3= '=' ( (lv_path_4_0= RULE_STRING ) ) otherlv_5= 'kind' otherlv_6= '=' ( (lv_kind_7_0= ruleArtifactKind ) ) otherlv_8= '}' ) ;
    public final EObject ruleArtifact() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_path_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Enumerator lv_kind_7_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:8119:2: ( (otherlv_0= 'Artifact' otherlv_1= '{' otherlv_2= 'path' otherlv_3= '=' ( (lv_path_4_0= RULE_STRING ) ) otherlv_5= 'kind' otherlv_6= '=' ( (lv_kind_7_0= ruleArtifactKind ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:8120:2: (otherlv_0= 'Artifact' otherlv_1= '{' otherlv_2= 'path' otherlv_3= '=' ( (lv_path_4_0= RULE_STRING ) ) otherlv_5= 'kind' otherlv_6= '=' ( (lv_kind_7_0= ruleArtifactKind ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:8120:2: (otherlv_0= 'Artifact' otherlv_1= '{' otherlv_2= 'path' otherlv_3= '=' ( (lv_path_4_0= RULE_STRING ) ) otherlv_5= 'kind' otherlv_6= '=' ( (lv_kind_7_0= ruleArtifactKind ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:8121:3: otherlv_0= 'Artifact' otherlv_1= '{' otherlv_2= 'path' otherlv_3= '=' ( (lv_path_4_0= RULE_STRING ) ) otherlv_5= 'kind' otherlv_6= '=' ( (lv_kind_7_0= ruleArtifactKind ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,231,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getArtifactAccess().getArtifactKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_234); 

            			newLeafNode(otherlv_1, grammarAccess.getArtifactAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,232,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getArtifactAccess().getPathKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_7); 

            			newLeafNode(otherlv_3, grammarAccess.getArtifactAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:8137:3: ( (lv_path_4_0= RULE_STRING ) )
            // InternalMlopsDsl.g:8138:4: (lv_path_4_0= RULE_STRING )
            {
            // InternalMlopsDsl.g:8138:4: (lv_path_4_0= RULE_STRING )
            // InternalMlopsDsl.g:8139:5: lv_path_4_0= RULE_STRING
            {
            lv_path_4_0=(Token)match(input,RULE_STRING,FOLLOW_9); 

            					newLeafNode(lv_path_4_0, grammarAccess.getArtifactAccess().getPathSTRINGTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getArtifactRule());
            					}
            					setWithLastConsumed(
            						current,
            						"path",
            						lv_path_4_0,
            						"org.eclipse.xtext.common.Terminals.STRING");
            				

            }


            }

            otherlv_5=(Token)match(input,18,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getArtifactAccess().getKindKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_235); 

            			newLeafNode(otherlv_6, grammarAccess.getArtifactAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:8163:3: ( (lv_kind_7_0= ruleArtifactKind ) )
            // InternalMlopsDsl.g:8164:4: (lv_kind_7_0= ruleArtifactKind )
            {
            // InternalMlopsDsl.g:8164:4: (lv_kind_7_0= ruleArtifactKind )
            // InternalMlopsDsl.g:8165:5: lv_kind_7_0= ruleArtifactKind
            {

            					newCompositeNode(grammarAccess.getArtifactAccess().getKindArtifactKindEnumRuleCall_7_0());
            				
            pushFollow(FOLLOW_8);
            lv_kind_7_0=ruleArtifactKind();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getArtifactRule());
            					}
            					set(
            						current,
            						"kind",
            						lv_kind_7_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.ArtifactKind");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getArtifactAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArtifact"


    // $ANTLR start "entryRuleMlTask"
    // InternalMlopsDsl.g:8190:1: entryRuleMlTask returns [EObject current=null] : iv_ruleMlTask= ruleMlTask EOF ;
    public final EObject entryRuleMlTask() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMlTask = null;


        try {
            // InternalMlopsDsl.g:8190:47: (iv_ruleMlTask= ruleMlTask EOF )
            // InternalMlopsDsl.g:8191:2: iv_ruleMlTask= ruleMlTask EOF
            {
             newCompositeNode(grammarAccess.getMlTaskRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMlTask=ruleMlTask();

            state._fsp--;

             current =iv_ruleMlTask; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMlTask"


    // $ANTLR start "ruleMlTask"
    // InternalMlopsDsl.g:8197:1: ruleMlTask returns [EObject current=null] : (otherlv_0= 'MlTask' otherlv_1= '{' otherlv_2= 'taskId' otherlv_3= '=' ( (lv_taskId_4_0= RULE_ID ) ) otherlv_5= 'type' otherlv_6= '=' ( (lv_type_7_0= ruleTaskType ) ) (otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']' )? (otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']' )? (otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']' )? otherlv_29= '}' ) ;
    public final EObject ruleMlTask() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token lv_taskId_4_0=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_12=null;
        Token otherlv_14=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        Token otherlv_17=null;
        Token otherlv_19=null;
        Token otherlv_21=null;
        Token otherlv_22=null;
        Token otherlv_23=null;
        Token otherlv_24=null;
        Token otherlv_26=null;
        Token otherlv_28=null;
        Token otherlv_29=null;
        Enumerator lv_type_7_0 = null;

        EObject lv_params_11_0 = null;

        EObject lv_params_13_0 = null;

        EObject lv_produces_18_0 = null;

        EObject lv_produces_20_0 = null;

        EObject lv_consumes_25_0 = null;

        EObject lv_consumes_27_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:8203:2: ( (otherlv_0= 'MlTask' otherlv_1= '{' otherlv_2= 'taskId' otherlv_3= '=' ( (lv_taskId_4_0= RULE_ID ) ) otherlv_5= 'type' otherlv_6= '=' ( (lv_type_7_0= ruleTaskType ) ) (otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']' )? (otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']' )? (otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']' )? otherlv_29= '}' ) )
            // InternalMlopsDsl.g:8204:2: (otherlv_0= 'MlTask' otherlv_1= '{' otherlv_2= 'taskId' otherlv_3= '=' ( (lv_taskId_4_0= RULE_ID ) ) otherlv_5= 'type' otherlv_6= '=' ( (lv_type_7_0= ruleTaskType ) ) (otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']' )? (otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']' )? (otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']' )? otherlv_29= '}' )
            {
            // InternalMlopsDsl.g:8204:2: (otherlv_0= 'MlTask' otherlv_1= '{' otherlv_2= 'taskId' otherlv_3= '=' ( (lv_taskId_4_0= RULE_ID ) ) otherlv_5= 'type' otherlv_6= '=' ( (lv_type_7_0= ruleTaskType ) ) (otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']' )? (otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']' )? (otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']' )? otherlv_29= '}' )
            // InternalMlopsDsl.g:8205:3: otherlv_0= 'MlTask' otherlv_1= '{' otherlv_2= 'taskId' otherlv_3= '=' ( (lv_taskId_4_0= RULE_ID ) ) otherlv_5= 'type' otherlv_6= '=' ( (lv_type_7_0= ruleTaskType ) ) (otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']' )? (otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']' )? (otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']' )? otherlv_29= '}'
            {
            otherlv_0=(Token)match(input,233,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMlTaskAccess().getMlTaskKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_236); 

            			newLeafNode(otherlv_1, grammarAccess.getMlTaskAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,234,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getMlTaskAccess().getTaskIdKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_3, grammarAccess.getMlTaskAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:8221:3: ( (lv_taskId_4_0= RULE_ID ) )
            // InternalMlopsDsl.g:8222:4: (lv_taskId_4_0= RULE_ID )
            {
            // InternalMlopsDsl.g:8222:4: (lv_taskId_4_0= RULE_ID )
            // InternalMlopsDsl.g:8223:5: lv_taskId_4_0= RULE_ID
            {
            lv_taskId_4_0=(Token)match(input,RULE_ID,FOLLOW_237); 

            					newLeafNode(lv_taskId_4_0, grammarAccess.getMlTaskAccess().getTaskIdIDTerminalRuleCall_4_0());
            				

            					if (current==null) {
            						current = createModelElement(grammarAccess.getMlTaskRule());
            					}
            					setWithLastConsumed(
            						current,
            						"taskId",
            						lv_taskId_4_0,
            						"org.eclipse.xtext.common.Terminals.ID");
            				

            }


            }

            otherlv_5=(Token)match(input,235,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getMlTaskAccess().getTypeKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_238); 

            			newLeafNode(otherlv_6, grammarAccess.getMlTaskAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:8247:3: ( (lv_type_7_0= ruleTaskType ) )
            // InternalMlopsDsl.g:8248:4: (lv_type_7_0= ruleTaskType )
            {
            // InternalMlopsDsl.g:8248:4: (lv_type_7_0= ruleTaskType )
            // InternalMlopsDsl.g:8249:5: lv_type_7_0= ruleTaskType
            {

            					newCompositeNode(grammarAccess.getMlTaskAccess().getTypeTaskTypeEnumRuleCall_7_0());
            				
            pushFollow(FOLLOW_239);
            lv_type_7_0=ruleTaskType();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMlTaskRule());
            					}
            					set(
            						current,
            						"type",
            						lv_type_7_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.TaskType");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:8266:3: (otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']' )?
            int alt147=2;
            int LA147_0 = input.LA(1);

            if ( (LA147_0==236) ) {
                alt147=1;
            }
            switch (alt147) {
                case 1 :
                    // InternalMlopsDsl.g:8267:4: otherlv_8= 'params' otherlv_9= '=' otherlv_10= '[' ( (lv_params_11_0= ruleParam ) ) (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )* otherlv_14= ']'
                    {
                    otherlv_8=(Token)match(input,236,FOLLOW_6); 

                    				newLeafNode(otherlv_8, grammarAccess.getMlTaskAccess().getParamsKeyword_8_0());
                    			
                    otherlv_9=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_9, grammarAccess.getMlTaskAccess().getEqualsSignKeyword_8_1());
                    			
                    otherlv_10=(Token)match(input,35,FOLLOW_76); 

                    				newLeafNode(otherlv_10, grammarAccess.getMlTaskAccess().getLeftSquareBracketKeyword_8_2());
                    			
                    // InternalMlopsDsl.g:8279:4: ( (lv_params_11_0= ruleParam ) )
                    // InternalMlopsDsl.g:8280:5: (lv_params_11_0= ruleParam )
                    {
                    // InternalMlopsDsl.g:8280:5: (lv_params_11_0= ruleParam )
                    // InternalMlopsDsl.g:8281:6: lv_params_11_0= ruleParam
                    {

                    						newCompositeNode(grammarAccess.getMlTaskAccess().getParamsParamParserRuleCall_8_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_params_11_0=ruleParam();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMlTaskRule());
                    						}
                    						add(
                    							current,
                    							"params",
                    							lv_params_11_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:8298:4: (otherlv_12= ',' ( (lv_params_13_0= ruleParam ) ) )*
                    loop146:
                    do {
                        int alt146=2;
                        int LA146_0 = input.LA(1);

                        if ( (LA146_0==36) ) {
                            alt146=1;
                        }


                        switch (alt146) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:8299:5: otherlv_12= ',' ( (lv_params_13_0= ruleParam ) )
                    	    {
                    	    otherlv_12=(Token)match(input,36,FOLLOW_76); 

                    	    					newLeafNode(otherlv_12, grammarAccess.getMlTaskAccess().getCommaKeyword_8_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:8303:5: ( (lv_params_13_0= ruleParam ) )
                    	    // InternalMlopsDsl.g:8304:6: (lv_params_13_0= ruleParam )
                    	    {
                    	    // InternalMlopsDsl.g:8304:6: (lv_params_13_0= ruleParam )
                    	    // InternalMlopsDsl.g:8305:7: lv_params_13_0= ruleParam
                    	    {

                    	    							newCompositeNode(grammarAccess.getMlTaskAccess().getParamsParamParserRuleCall_8_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_params_13_0=ruleParam();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getMlTaskRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"params",
                    	    								lv_params_13_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Param");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop146;
                        }
                    } while (true);

                    otherlv_14=(Token)match(input,37,FOLLOW_240); 

                    				newLeafNode(otherlv_14, grammarAccess.getMlTaskAccess().getRightSquareBracketKeyword_8_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:8328:3: (otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']' )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==237) ) {
                alt149=1;
            }
            switch (alt149) {
                case 1 :
                    // InternalMlopsDsl.g:8329:4: otherlv_15= 'produces' otherlv_16= '=' otherlv_17= '[' ( (lv_produces_18_0= ruleArtifact ) ) (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )* otherlv_21= ']'
                    {
                    otherlv_15=(Token)match(input,237,FOLLOW_6); 

                    				newLeafNode(otherlv_15, grammarAccess.getMlTaskAccess().getProducesKeyword_9_0());
                    			
                    otherlv_16=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_16, grammarAccess.getMlTaskAccess().getEqualsSignKeyword_9_1());
                    			
                    otherlv_17=(Token)match(input,35,FOLLOW_241); 

                    				newLeafNode(otherlv_17, grammarAccess.getMlTaskAccess().getLeftSquareBracketKeyword_9_2());
                    			
                    // InternalMlopsDsl.g:8341:4: ( (lv_produces_18_0= ruleArtifact ) )
                    // InternalMlopsDsl.g:8342:5: (lv_produces_18_0= ruleArtifact )
                    {
                    // InternalMlopsDsl.g:8342:5: (lv_produces_18_0= ruleArtifact )
                    // InternalMlopsDsl.g:8343:6: lv_produces_18_0= ruleArtifact
                    {

                    						newCompositeNode(grammarAccess.getMlTaskAccess().getProducesArtifactParserRuleCall_9_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_produces_18_0=ruleArtifact();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMlTaskRule());
                    						}
                    						add(
                    							current,
                    							"produces",
                    							lv_produces_18_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Artifact");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:8360:4: (otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) ) )*
                    loop148:
                    do {
                        int alt148=2;
                        int LA148_0 = input.LA(1);

                        if ( (LA148_0==36) ) {
                            alt148=1;
                        }


                        switch (alt148) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:8361:5: otherlv_19= ',' ( (lv_produces_20_0= ruleArtifact ) )
                    	    {
                    	    otherlv_19=(Token)match(input,36,FOLLOW_241); 

                    	    					newLeafNode(otherlv_19, grammarAccess.getMlTaskAccess().getCommaKeyword_9_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:8365:5: ( (lv_produces_20_0= ruleArtifact ) )
                    	    // InternalMlopsDsl.g:8366:6: (lv_produces_20_0= ruleArtifact )
                    	    {
                    	    // InternalMlopsDsl.g:8366:6: (lv_produces_20_0= ruleArtifact )
                    	    // InternalMlopsDsl.g:8367:7: lv_produces_20_0= ruleArtifact
                    	    {

                    	    							newCompositeNode(grammarAccess.getMlTaskAccess().getProducesArtifactParserRuleCall_9_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_produces_20_0=ruleArtifact();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getMlTaskRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"produces",
                    	    								lv_produces_20_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Artifact");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop148;
                        }
                    } while (true);

                    otherlv_21=(Token)match(input,37,FOLLOW_242); 

                    				newLeafNode(otherlv_21, grammarAccess.getMlTaskAccess().getRightSquareBracketKeyword_9_5());
                    			

                    }
                    break;

            }

            // InternalMlopsDsl.g:8390:3: (otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']' )?
            int alt151=2;
            int LA151_0 = input.LA(1);

            if ( (LA151_0==238) ) {
                alt151=1;
            }
            switch (alt151) {
                case 1 :
                    // InternalMlopsDsl.g:8391:4: otherlv_22= 'consumes' otherlv_23= '=' otherlv_24= '[' ( (lv_consumes_25_0= ruleArtifact ) ) (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )* otherlv_28= ']'
                    {
                    otherlv_22=(Token)match(input,238,FOLLOW_6); 

                    				newLeafNode(otherlv_22, grammarAccess.getMlTaskAccess().getConsumesKeyword_10_0());
                    			
                    otherlv_23=(Token)match(input,15,FOLLOW_24); 

                    				newLeafNode(otherlv_23, grammarAccess.getMlTaskAccess().getEqualsSignKeyword_10_1());
                    			
                    otherlv_24=(Token)match(input,35,FOLLOW_241); 

                    				newLeafNode(otherlv_24, grammarAccess.getMlTaskAccess().getLeftSquareBracketKeyword_10_2());
                    			
                    // InternalMlopsDsl.g:8403:4: ( (lv_consumes_25_0= ruleArtifact ) )
                    // InternalMlopsDsl.g:8404:5: (lv_consumes_25_0= ruleArtifact )
                    {
                    // InternalMlopsDsl.g:8404:5: (lv_consumes_25_0= ruleArtifact )
                    // InternalMlopsDsl.g:8405:6: lv_consumes_25_0= ruleArtifact
                    {

                    						newCompositeNode(grammarAccess.getMlTaskAccess().getConsumesArtifactParserRuleCall_10_3_0());
                    					
                    pushFollow(FOLLOW_26);
                    lv_consumes_25_0=ruleArtifact();

                    state._fsp--;


                    						if (current==null) {
                    							current = createModelElementForParent(grammarAccess.getMlTaskRule());
                    						}
                    						add(
                    							current,
                    							"consumes",
                    							lv_consumes_25_0,
                    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Artifact");
                    						afterParserOrEnumRuleCall();
                    					

                    }


                    }

                    // InternalMlopsDsl.g:8422:4: (otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) ) )*
                    loop150:
                    do {
                        int alt150=2;
                        int LA150_0 = input.LA(1);

                        if ( (LA150_0==36) ) {
                            alt150=1;
                        }


                        switch (alt150) {
                    	case 1 :
                    	    // InternalMlopsDsl.g:8423:5: otherlv_26= ',' ( (lv_consumes_27_0= ruleArtifact ) )
                    	    {
                    	    otherlv_26=(Token)match(input,36,FOLLOW_241); 

                    	    					newLeafNode(otherlv_26, grammarAccess.getMlTaskAccess().getCommaKeyword_10_4_0());
                    	    				
                    	    // InternalMlopsDsl.g:8427:5: ( (lv_consumes_27_0= ruleArtifact ) )
                    	    // InternalMlopsDsl.g:8428:6: (lv_consumes_27_0= ruleArtifact )
                    	    {
                    	    // InternalMlopsDsl.g:8428:6: (lv_consumes_27_0= ruleArtifact )
                    	    // InternalMlopsDsl.g:8429:7: lv_consumes_27_0= ruleArtifact
                    	    {

                    	    							newCompositeNode(grammarAccess.getMlTaskAccess().getConsumesArtifactParserRuleCall_10_4_1_0());
                    	    						
                    	    pushFollow(FOLLOW_26);
                    	    lv_consumes_27_0=ruleArtifact();

                    	    state._fsp--;


                    	    							if (current==null) {
                    	    								current = createModelElementForParent(grammarAccess.getMlTaskRule());
                    	    							}
                    	    							add(
                    	    								current,
                    	    								"consumes",
                    	    								lv_consumes_27_0,
                    	    								"org.xtext.ucd.mlopsdsl.MlopsDsl.Artifact");
                    	    							afterParserOrEnumRuleCall();
                    	    						

                    	    }


                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop150;
                        }
                    } while (true);

                    otherlv_28=(Token)match(input,37,FOLLOW_8); 

                    				newLeafNode(otherlv_28, grammarAccess.getMlTaskAccess().getRightSquareBracketKeyword_10_5());
                    			

                    }
                    break;

            }

            otherlv_29=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_29, grammarAccess.getMlTaskAccess().getRightCurlyBracketKeyword_11());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMlTask"


    // $ANTLR start "entryRuleEdge"
    // InternalMlopsDsl.g:8460:1: entryRuleEdge returns [EObject current=null] : iv_ruleEdge= ruleEdge EOF ;
    public final EObject entryRuleEdge() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleEdge = null;


        try {
            // InternalMlopsDsl.g:8460:45: (iv_ruleEdge= ruleEdge EOF )
            // InternalMlopsDsl.g:8461:2: iv_ruleEdge= ruleEdge EOF
            {
             newCompositeNode(grammarAccess.getEdgeRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleEdge=ruleEdge();

            state._fsp--;

             current =iv_ruleEdge; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleEdge"


    // $ANTLR start "ruleEdge"
    // InternalMlopsDsl.g:8467:1: ruleEdge returns [EObject current=null] : (otherlv_0= 'Edge' otherlv_1= '{' otherlv_2= 'from' otherlv_3= '=' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'to' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= '}' ) ;
    public final EObject ruleEdge() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_5=null;
        Token otherlv_6=null;
        Token otherlv_7=null;
        Token otherlv_8=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8473:2: ( (otherlv_0= 'Edge' otherlv_1= '{' otherlv_2= 'from' otherlv_3= '=' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'to' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= '}' ) )
            // InternalMlopsDsl.g:8474:2: (otherlv_0= 'Edge' otherlv_1= '{' otherlv_2= 'from' otherlv_3= '=' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'to' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= '}' )
            {
            // InternalMlopsDsl.g:8474:2: (otherlv_0= 'Edge' otherlv_1= '{' otherlv_2= 'from' otherlv_3= '=' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'to' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= '}' )
            // InternalMlopsDsl.g:8475:3: otherlv_0= 'Edge' otherlv_1= '{' otherlv_2= 'from' otherlv_3= '=' ( (otherlv_4= RULE_ID ) ) otherlv_5= 'to' otherlv_6= '=' ( (otherlv_7= RULE_ID ) ) otherlv_8= '}'
            {
            otherlv_0=(Token)match(input,239,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getEdgeAccess().getEdgeKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_243); 

            			newLeafNode(otherlv_1, grammarAccess.getEdgeAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,240,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getEdgeAccess().getFromKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_3, grammarAccess.getEdgeAccess().getEqualsSignKeyword_3());
            		
            // InternalMlopsDsl.g:8491:3: ( (otherlv_4= RULE_ID ) )
            // InternalMlopsDsl.g:8492:4: (otherlv_4= RULE_ID )
            {
            // InternalMlopsDsl.g:8492:4: (otherlv_4= RULE_ID )
            // InternalMlopsDsl.g:8493:5: otherlv_4= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEdgeRule());
            					}
            				
            otherlv_4=(Token)match(input,RULE_ID,FOLLOW_244); 

            					newLeafNode(otherlv_4, grammarAccess.getEdgeAccess().getFromMlTaskCrossReference_4_0());
            				

            }


            }

            otherlv_5=(Token)match(input,241,FOLLOW_6); 

            			newLeafNode(otherlv_5, grammarAccess.getEdgeAccess().getToKeyword_5());
            		
            otherlv_6=(Token)match(input,15,FOLLOW_32); 

            			newLeafNode(otherlv_6, grammarAccess.getEdgeAccess().getEqualsSignKeyword_6());
            		
            // InternalMlopsDsl.g:8512:3: ( (otherlv_7= RULE_ID ) )
            // InternalMlopsDsl.g:8513:4: (otherlv_7= RULE_ID )
            {
            // InternalMlopsDsl.g:8513:4: (otherlv_7= RULE_ID )
            // InternalMlopsDsl.g:8514:5: otherlv_7= RULE_ID
            {

            					if (current==null) {
            						current = createModelElement(grammarAccess.getEdgeRule());
            					}
            				
            otherlv_7=(Token)match(input,RULE_ID,FOLLOW_8); 

            					newLeafNode(otherlv_7, grammarAccess.getEdgeAccess().getToMlTaskCrossReference_7_0());
            				

            }


            }

            otherlv_8=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_8, grammarAccess.getEdgeAccess().getRightCurlyBracketKeyword_8());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleEdge"


    // $ANTLR start "entryRuleMlWorkflow"
    // InternalMlopsDsl.g:8533:1: entryRuleMlWorkflow returns [EObject current=null] : iv_ruleMlWorkflow= ruleMlWorkflow EOF ;
    public final EObject entryRuleMlWorkflow() throws RecognitionException {
        EObject current = null;

        EObject iv_ruleMlWorkflow = null;


        try {
            // InternalMlopsDsl.g:8533:51: (iv_ruleMlWorkflow= ruleMlWorkflow EOF )
            // InternalMlopsDsl.g:8534:2: iv_ruleMlWorkflow= ruleMlWorkflow EOF
            {
             newCompositeNode(grammarAccess.getMlWorkflowRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleMlWorkflow=ruleMlWorkflow();

            state._fsp--;

             current =iv_ruleMlWorkflow; 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleMlWorkflow"


    // $ANTLR start "ruleMlWorkflow"
    // InternalMlopsDsl.g:8540:1: ruleMlWorkflow returns [EObject current=null] : (otherlv_0= 'MlWorkflow' otherlv_1= '{' otherlv_2= 'tasks' otherlv_3= '=' otherlv_4= '[' ( (lv_tasks_5_0= ruleMlTask ) ) (otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) ) )* otherlv_8= ']' otherlv_9= 'edges' otherlv_10= '=' otherlv_11= '[' ( (lv_edges_12_0= ruleEdge ) ) (otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) ) )* otherlv_15= ']' otherlv_16= '}' ) ;
    public final EObject ruleMlWorkflow() throws RecognitionException {
        EObject current = null;

        Token otherlv_0=null;
        Token otherlv_1=null;
        Token otherlv_2=null;
        Token otherlv_3=null;
        Token otherlv_4=null;
        Token otherlv_6=null;
        Token otherlv_8=null;
        Token otherlv_9=null;
        Token otherlv_10=null;
        Token otherlv_11=null;
        Token otherlv_13=null;
        Token otherlv_15=null;
        Token otherlv_16=null;
        EObject lv_tasks_5_0 = null;

        EObject lv_tasks_7_0 = null;

        EObject lv_edges_12_0 = null;

        EObject lv_edges_14_0 = null;



        	enterRule();

        try {
            // InternalMlopsDsl.g:8546:2: ( (otherlv_0= 'MlWorkflow' otherlv_1= '{' otherlv_2= 'tasks' otherlv_3= '=' otherlv_4= '[' ( (lv_tasks_5_0= ruleMlTask ) ) (otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) ) )* otherlv_8= ']' otherlv_9= 'edges' otherlv_10= '=' otherlv_11= '[' ( (lv_edges_12_0= ruleEdge ) ) (otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) ) )* otherlv_15= ']' otherlv_16= '}' ) )
            // InternalMlopsDsl.g:8547:2: (otherlv_0= 'MlWorkflow' otherlv_1= '{' otherlv_2= 'tasks' otherlv_3= '=' otherlv_4= '[' ( (lv_tasks_5_0= ruleMlTask ) ) (otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) ) )* otherlv_8= ']' otherlv_9= 'edges' otherlv_10= '=' otherlv_11= '[' ( (lv_edges_12_0= ruleEdge ) ) (otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) ) )* otherlv_15= ']' otherlv_16= '}' )
            {
            // InternalMlopsDsl.g:8547:2: (otherlv_0= 'MlWorkflow' otherlv_1= '{' otherlv_2= 'tasks' otherlv_3= '=' otherlv_4= '[' ( (lv_tasks_5_0= ruleMlTask ) ) (otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) ) )* otherlv_8= ']' otherlv_9= 'edges' otherlv_10= '=' otherlv_11= '[' ( (lv_edges_12_0= ruleEdge ) ) (otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) ) )* otherlv_15= ']' otherlv_16= '}' )
            // InternalMlopsDsl.g:8548:3: otherlv_0= 'MlWorkflow' otherlv_1= '{' otherlv_2= 'tasks' otherlv_3= '=' otherlv_4= '[' ( (lv_tasks_5_0= ruleMlTask ) ) (otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) ) )* otherlv_8= ']' otherlv_9= 'edges' otherlv_10= '=' otherlv_11= '[' ( (lv_edges_12_0= ruleEdge ) ) (otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) ) )* otherlv_15= ']' otherlv_16= '}'
            {
            otherlv_0=(Token)match(input,242,FOLLOW_4); 

            			newLeafNode(otherlv_0, grammarAccess.getMlWorkflowAccess().getMlWorkflowKeyword_0());
            		
            otherlv_1=(Token)match(input,13,FOLLOW_245); 

            			newLeafNode(otherlv_1, grammarAccess.getMlWorkflowAccess().getLeftCurlyBracketKeyword_1());
            		
            otherlv_2=(Token)match(input,243,FOLLOW_6); 

            			newLeafNode(otherlv_2, grammarAccess.getMlWorkflowAccess().getTasksKeyword_2());
            		
            otherlv_3=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_3, grammarAccess.getMlWorkflowAccess().getEqualsSignKeyword_3());
            		
            otherlv_4=(Token)match(input,35,FOLLOW_246); 

            			newLeafNode(otherlv_4, grammarAccess.getMlWorkflowAccess().getLeftSquareBracketKeyword_4());
            		
            // InternalMlopsDsl.g:8568:3: ( (lv_tasks_5_0= ruleMlTask ) )
            // InternalMlopsDsl.g:8569:4: (lv_tasks_5_0= ruleMlTask )
            {
            // InternalMlopsDsl.g:8569:4: (lv_tasks_5_0= ruleMlTask )
            // InternalMlopsDsl.g:8570:5: lv_tasks_5_0= ruleMlTask
            {

            					newCompositeNode(grammarAccess.getMlWorkflowAccess().getTasksMlTaskParserRuleCall_5_0());
            				
            pushFollow(FOLLOW_26);
            lv_tasks_5_0=ruleMlTask();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMlWorkflowRule());
            					}
            					add(
            						current,
            						"tasks",
            						lv_tasks_5_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.MlTask");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:8587:3: (otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) ) )*
            loop152:
            do {
                int alt152=2;
                int LA152_0 = input.LA(1);

                if ( (LA152_0==36) ) {
                    alt152=1;
                }


                switch (alt152) {
            	case 1 :
            	    // InternalMlopsDsl.g:8588:4: otherlv_6= ',' ( (lv_tasks_7_0= ruleMlTask ) )
            	    {
            	    otherlv_6=(Token)match(input,36,FOLLOW_246); 

            	    				newLeafNode(otherlv_6, grammarAccess.getMlWorkflowAccess().getCommaKeyword_6_0());
            	    			
            	    // InternalMlopsDsl.g:8592:4: ( (lv_tasks_7_0= ruleMlTask ) )
            	    // InternalMlopsDsl.g:8593:5: (lv_tasks_7_0= ruleMlTask )
            	    {
            	    // InternalMlopsDsl.g:8593:5: (lv_tasks_7_0= ruleMlTask )
            	    // InternalMlopsDsl.g:8594:6: lv_tasks_7_0= ruleMlTask
            	    {

            	    						newCompositeNode(grammarAccess.getMlWorkflowAccess().getTasksMlTaskParserRuleCall_6_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_tasks_7_0=ruleMlTask();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMlWorkflowRule());
            	    						}
            	    						add(
            	    							current,
            	    							"tasks",
            	    							lv_tasks_7_0,
            	    							"org.xtext.ucd.mlopsdsl.MlopsDsl.MlTask");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop152;
                }
            } while (true);

            otherlv_8=(Token)match(input,37,FOLLOW_247); 

            			newLeafNode(otherlv_8, grammarAccess.getMlWorkflowAccess().getRightSquareBracketKeyword_7());
            		
            otherlv_9=(Token)match(input,244,FOLLOW_6); 

            			newLeafNode(otherlv_9, grammarAccess.getMlWorkflowAccess().getEdgesKeyword_8());
            		
            otherlv_10=(Token)match(input,15,FOLLOW_24); 

            			newLeafNode(otherlv_10, grammarAccess.getMlWorkflowAccess().getEqualsSignKeyword_9());
            		
            otherlv_11=(Token)match(input,35,FOLLOW_248); 

            			newLeafNode(otherlv_11, grammarAccess.getMlWorkflowAccess().getLeftSquareBracketKeyword_10());
            		
            // InternalMlopsDsl.g:8628:3: ( (lv_edges_12_0= ruleEdge ) )
            // InternalMlopsDsl.g:8629:4: (lv_edges_12_0= ruleEdge )
            {
            // InternalMlopsDsl.g:8629:4: (lv_edges_12_0= ruleEdge )
            // InternalMlopsDsl.g:8630:5: lv_edges_12_0= ruleEdge
            {

            					newCompositeNode(grammarAccess.getMlWorkflowAccess().getEdgesEdgeParserRuleCall_11_0());
            				
            pushFollow(FOLLOW_26);
            lv_edges_12_0=ruleEdge();

            state._fsp--;


            					if (current==null) {
            						current = createModelElementForParent(grammarAccess.getMlWorkflowRule());
            					}
            					add(
            						current,
            						"edges",
            						lv_edges_12_0,
            						"org.xtext.ucd.mlopsdsl.MlopsDsl.Edge");
            					afterParserOrEnumRuleCall();
            				

            }


            }

            // InternalMlopsDsl.g:8647:3: (otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) ) )*
            loop153:
            do {
                int alt153=2;
                int LA153_0 = input.LA(1);

                if ( (LA153_0==36) ) {
                    alt153=1;
                }


                switch (alt153) {
            	case 1 :
            	    // InternalMlopsDsl.g:8648:4: otherlv_13= ',' ( (lv_edges_14_0= ruleEdge ) )
            	    {
            	    otherlv_13=(Token)match(input,36,FOLLOW_248); 

            	    				newLeafNode(otherlv_13, grammarAccess.getMlWorkflowAccess().getCommaKeyword_12_0());
            	    			
            	    // InternalMlopsDsl.g:8652:4: ( (lv_edges_14_0= ruleEdge ) )
            	    // InternalMlopsDsl.g:8653:5: (lv_edges_14_0= ruleEdge )
            	    {
            	    // InternalMlopsDsl.g:8653:5: (lv_edges_14_0= ruleEdge )
            	    // InternalMlopsDsl.g:8654:6: lv_edges_14_0= ruleEdge
            	    {

            	    						newCompositeNode(grammarAccess.getMlWorkflowAccess().getEdgesEdgeParserRuleCall_12_1_0());
            	    					
            	    pushFollow(FOLLOW_26);
            	    lv_edges_14_0=ruleEdge();

            	    state._fsp--;


            	    						if (current==null) {
            	    							current = createModelElementForParent(grammarAccess.getMlWorkflowRule());
            	    						}
            	    						add(
            	    							current,
            	    							"edges",
            	    							lv_edges_14_0,
            	    							"org.xtext.ucd.mlopsdsl.MlopsDsl.Edge");
            	    						afterParserOrEnumRuleCall();
            	    					

            	    }


            	    }


            	    }
            	    break;

            	default :
            	    break loop153;
                }
            } while (true);

            otherlv_15=(Token)match(input,37,FOLLOW_8); 

            			newLeafNode(otherlv_15, grammarAccess.getMlWorkflowAccess().getRightSquareBracketKeyword_13());
            		
            otherlv_16=(Token)match(input,16,FOLLOW_2); 

            			newLeafNode(otherlv_16, grammarAccess.getMlWorkflowAccess().getRightCurlyBracketKeyword_14());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleMlWorkflow"


    // $ANTLR start "entryRuleSignedLong"
    // InternalMlopsDsl.g:8684:1: entryRuleSignedLong returns [String current=null] : iv_ruleSignedLong= ruleSignedLong EOF ;
    public final String entryRuleSignedLong() throws RecognitionException {
        String current = null;

        AntlrDatatypeRuleToken iv_ruleSignedLong = null;


        try {
            // InternalMlopsDsl.g:8684:50: (iv_ruleSignedLong= ruleSignedLong EOF )
            // InternalMlopsDsl.g:8685:2: iv_ruleSignedLong= ruleSignedLong EOF
            {
             newCompositeNode(grammarAccess.getSignedLongRule()); 
            pushFollow(FOLLOW_1);
            iv_ruleSignedLong=ruleSignedLong();

            state._fsp--;

             current =iv_ruleSignedLong.getText(); 
            match(input,EOF,FOLLOW_2); 

            }

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "entryRuleSignedLong"


    // $ANTLR start "ruleSignedLong"
    // InternalMlopsDsl.g:8691:1: ruleSignedLong returns [AntlrDatatypeRuleToken current=new AntlrDatatypeRuleToken()] : ( (kw= '+' | kw= '-' )? this_INT_2= RULE_INT ) ;
    public final AntlrDatatypeRuleToken ruleSignedLong() throws RecognitionException {
        AntlrDatatypeRuleToken current = new AntlrDatatypeRuleToken();

        Token kw=null;
        Token this_INT_2=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8697:2: ( ( (kw= '+' | kw= '-' )? this_INT_2= RULE_INT ) )
            // InternalMlopsDsl.g:8698:2: ( (kw= '+' | kw= '-' )? this_INT_2= RULE_INT )
            {
            // InternalMlopsDsl.g:8698:2: ( (kw= '+' | kw= '-' )? this_INT_2= RULE_INT )
            // InternalMlopsDsl.g:8699:3: (kw= '+' | kw= '-' )? this_INT_2= RULE_INT
            {
            // InternalMlopsDsl.g:8699:3: (kw= '+' | kw= '-' )?
            int alt154=3;
            int LA154_0 = input.LA(1);

            if ( (LA154_0==245) ) {
                alt154=1;
            }
            else if ( (LA154_0==246) ) {
                alt154=2;
            }
            switch (alt154) {
                case 1 :
                    // InternalMlopsDsl.g:8700:4: kw= '+'
                    {
                    kw=(Token)match(input,245,FOLLOW_17); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSignedLongAccess().getPlusSignKeyword_0_0());
                    			

                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8706:4: kw= '-'
                    {
                    kw=(Token)match(input,246,FOLLOW_17); 

                    				current.merge(kw);
                    				newLeafNode(kw, grammarAccess.getSignedLongAccess().getHyphenMinusKeyword_0_1());
                    			

                    }
                    break;

            }

            this_INT_2=(Token)match(input,RULE_INT,FOLLOW_2); 

            			current.merge(this_INT_2);
            		

            			newLeafNode(this_INT_2, grammarAccess.getSignedLongAccess().getINTTerminalRuleCall_1());
            		

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSignedLong"


    // $ANTLR start "ruleComputeProfile"
    // InternalMlopsDsl.g:8723:1: ruleComputeProfile returns [Enumerator current=null] : ( (enumLiteral_0= 'CPU_SMALL' ) | (enumLiteral_1= 'GPU_SINGLE' ) | (enumLiteral_2= 'GPU_MULTI' ) ) ;
    public final Enumerator ruleComputeProfile() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8729:2: ( ( (enumLiteral_0= 'CPU_SMALL' ) | (enumLiteral_1= 'GPU_SINGLE' ) | (enumLiteral_2= 'GPU_MULTI' ) ) )
            // InternalMlopsDsl.g:8730:2: ( (enumLiteral_0= 'CPU_SMALL' ) | (enumLiteral_1= 'GPU_SINGLE' ) | (enumLiteral_2= 'GPU_MULTI' ) )
            {
            // InternalMlopsDsl.g:8730:2: ( (enumLiteral_0= 'CPU_SMALL' ) | (enumLiteral_1= 'GPU_SINGLE' ) | (enumLiteral_2= 'GPU_MULTI' ) )
            int alt155=3;
            switch ( input.LA(1) ) {
            case 247:
                {
                alt155=1;
                }
                break;
            case 248:
                {
                alt155=2;
                }
                break;
            case 249:
                {
                alt155=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 155, 0, input);

                throw nvae;
            }

            switch (alt155) {
                case 1 :
                    // InternalMlopsDsl.g:8731:3: (enumLiteral_0= 'CPU_SMALL' )
                    {
                    // InternalMlopsDsl.g:8731:3: (enumLiteral_0= 'CPU_SMALL' )
                    // InternalMlopsDsl.g:8732:4: enumLiteral_0= 'CPU_SMALL'
                    {
                    enumLiteral_0=(Token)match(input,247,FOLLOW_2); 

                    				current = grammarAccess.getComputeProfileAccess().getCPU_SMALLEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getComputeProfileAccess().getCPU_SMALLEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8739:3: (enumLiteral_1= 'GPU_SINGLE' )
                    {
                    // InternalMlopsDsl.g:8739:3: (enumLiteral_1= 'GPU_SINGLE' )
                    // InternalMlopsDsl.g:8740:4: enumLiteral_1= 'GPU_SINGLE'
                    {
                    enumLiteral_1=(Token)match(input,248,FOLLOW_2); 

                    				current = grammarAccess.getComputeProfileAccess().getGPU_SINGLEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getComputeProfileAccess().getGPU_SINGLEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:8747:3: (enumLiteral_2= 'GPU_MULTI' )
                    {
                    // InternalMlopsDsl.g:8747:3: (enumLiteral_2= 'GPU_MULTI' )
                    // InternalMlopsDsl.g:8748:4: enumLiteral_2= 'GPU_MULTI'
                    {
                    enumLiteral_2=(Token)match(input,249,FOLLOW_2); 

                    				current = grammarAccess.getComputeProfileAccess().getGPU_MULTIEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getComputeProfileAccess().getGPU_MULTIEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleComputeProfile"


    // $ANTLR start "rulePackaging"
    // InternalMlopsDsl.g:8758:1: rulePackaging returns [Enumerator current=null] : ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'WHEEL' ) | (enumLiteral_2= 'CONTAINER' ) ) ;
    public final Enumerator rulePackaging() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8764:2: ( ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'WHEEL' ) | (enumLiteral_2= 'CONTAINER' ) ) )
            // InternalMlopsDsl.g:8765:2: ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'WHEEL' ) | (enumLiteral_2= 'CONTAINER' ) )
            {
            // InternalMlopsDsl.g:8765:2: ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'WHEEL' ) | (enumLiteral_2= 'CONTAINER' ) )
            int alt156=3;
            switch ( input.LA(1) ) {
            case 250:
                {
                alt156=1;
                }
                break;
            case 251:
                {
                alt156=2;
                }
                break;
            case 252:
                {
                alt156=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 156, 0, input);

                throw nvae;
            }

            switch (alt156) {
                case 1 :
                    // InternalMlopsDsl.g:8766:3: (enumLiteral_0= 'NONE' )
                    {
                    // InternalMlopsDsl.g:8766:3: (enumLiteral_0= 'NONE' )
                    // InternalMlopsDsl.g:8767:4: enumLiteral_0= 'NONE'
                    {
                    enumLiteral_0=(Token)match(input,250,FOLLOW_2); 

                    				current = grammarAccess.getPackagingAccess().getNONEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getPackagingAccess().getNONEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8774:3: (enumLiteral_1= 'WHEEL' )
                    {
                    // InternalMlopsDsl.g:8774:3: (enumLiteral_1= 'WHEEL' )
                    // InternalMlopsDsl.g:8775:4: enumLiteral_1= 'WHEEL'
                    {
                    enumLiteral_1=(Token)match(input,251,FOLLOW_2); 

                    				current = grammarAccess.getPackagingAccess().getWHEELEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getPackagingAccess().getWHEELEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:8782:3: (enumLiteral_2= 'CONTAINER' )
                    {
                    // InternalMlopsDsl.g:8782:3: (enumLiteral_2= 'CONTAINER' )
                    // InternalMlopsDsl.g:8783:4: enumLiteral_2= 'CONTAINER'
                    {
                    enumLiteral_2=(Token)match(input,252,FOLLOW_2); 

                    				current = grammarAccess.getPackagingAccess().getCONTAINEREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getPackagingAccess().getCONTAINEREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePackaging"


    // $ANTLR start "ruleServingKind"
    // InternalMlopsDsl.g:8793:1: ruleServingKind returns [Enumerator current=null] : ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'API' ) | (enumLiteral_2= 'BATCH' ) | (enumLiteral_3= 'APP' ) ) ;
    public final Enumerator ruleServingKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8799:2: ( ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'API' ) | (enumLiteral_2= 'BATCH' ) | (enumLiteral_3= 'APP' ) ) )
            // InternalMlopsDsl.g:8800:2: ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'API' ) | (enumLiteral_2= 'BATCH' ) | (enumLiteral_3= 'APP' ) )
            {
            // InternalMlopsDsl.g:8800:2: ( (enumLiteral_0= 'NONE' ) | (enumLiteral_1= 'API' ) | (enumLiteral_2= 'BATCH' ) | (enumLiteral_3= 'APP' ) )
            int alt157=4;
            switch ( input.LA(1) ) {
            case 250:
                {
                alt157=1;
                }
                break;
            case 253:
                {
                alt157=2;
                }
                break;
            case 254:
                {
                alt157=3;
                }
                break;
            case 255:
                {
                alt157=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 157, 0, input);

                throw nvae;
            }

            switch (alt157) {
                case 1 :
                    // InternalMlopsDsl.g:8801:3: (enumLiteral_0= 'NONE' )
                    {
                    // InternalMlopsDsl.g:8801:3: (enumLiteral_0= 'NONE' )
                    // InternalMlopsDsl.g:8802:4: enumLiteral_0= 'NONE'
                    {
                    enumLiteral_0=(Token)match(input,250,FOLLOW_2); 

                    				current = grammarAccess.getServingKindAccess().getNONEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getServingKindAccess().getNONEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8809:3: (enumLiteral_1= 'API' )
                    {
                    // InternalMlopsDsl.g:8809:3: (enumLiteral_1= 'API' )
                    // InternalMlopsDsl.g:8810:4: enumLiteral_1= 'API'
                    {
                    enumLiteral_1=(Token)match(input,253,FOLLOW_2); 

                    				current = grammarAccess.getServingKindAccess().getAPIEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getServingKindAccess().getAPIEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:8817:3: (enumLiteral_2= 'BATCH' )
                    {
                    // InternalMlopsDsl.g:8817:3: (enumLiteral_2= 'BATCH' )
                    // InternalMlopsDsl.g:8818:4: enumLiteral_2= 'BATCH'
                    {
                    enumLiteral_2=(Token)match(input,254,FOLLOW_2); 

                    				current = grammarAccess.getServingKindAccess().getBATCHEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getServingKindAccess().getBATCHEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:8825:3: (enumLiteral_3= 'APP' )
                    {
                    // InternalMlopsDsl.g:8825:3: (enumLiteral_3= 'APP' )
                    // InternalMlopsDsl.g:8826:4: enumLiteral_3= 'APP'
                    {
                    enumLiteral_3=(Token)match(input,255,FOLLOW_2); 

                    				current = grammarAccess.getServingKindAccess().getAPPEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getServingKindAccess().getAPPEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleServingKind"


    // $ANTLR start "ruleTaskType"
    // InternalMlopsDsl.g:8836:1: ruleTaskType returns [Enumerator current=null] : ( (enumLiteral_0= 'PREPARE' ) | (enumLiteral_1= 'TRAIN' ) | (enumLiteral_2= 'EVAL' ) | (enumLiteral_3= 'PACKAGE' ) | (enumLiteral_4= 'REGISTER' ) | (enumLiteral_5= 'SERVE' ) | (enumLiteral_6= 'MONITOR' ) | (enumLiteral_7= 'VERSION' ) | (enumLiteral_8= 'TRACK' ) | (enumLiteral_9= 'DECIDE' ) | (enumLiteral_10= 'DEPLOY' ) | (enumLiteral_11= 'RETRAIN' ) | (enumLiteral_12= 'GOVERN' ) ) ;
    public final Enumerator ruleTaskType() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;
        Token enumLiteral_11=null;
        Token enumLiteral_12=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8842:2: ( ( (enumLiteral_0= 'PREPARE' ) | (enumLiteral_1= 'TRAIN' ) | (enumLiteral_2= 'EVAL' ) | (enumLiteral_3= 'PACKAGE' ) | (enumLiteral_4= 'REGISTER' ) | (enumLiteral_5= 'SERVE' ) | (enumLiteral_6= 'MONITOR' ) | (enumLiteral_7= 'VERSION' ) | (enumLiteral_8= 'TRACK' ) | (enumLiteral_9= 'DECIDE' ) | (enumLiteral_10= 'DEPLOY' ) | (enumLiteral_11= 'RETRAIN' ) | (enumLiteral_12= 'GOVERN' ) ) )
            // InternalMlopsDsl.g:8843:2: ( (enumLiteral_0= 'PREPARE' ) | (enumLiteral_1= 'TRAIN' ) | (enumLiteral_2= 'EVAL' ) | (enumLiteral_3= 'PACKAGE' ) | (enumLiteral_4= 'REGISTER' ) | (enumLiteral_5= 'SERVE' ) | (enumLiteral_6= 'MONITOR' ) | (enumLiteral_7= 'VERSION' ) | (enumLiteral_8= 'TRACK' ) | (enumLiteral_9= 'DECIDE' ) | (enumLiteral_10= 'DEPLOY' ) | (enumLiteral_11= 'RETRAIN' ) | (enumLiteral_12= 'GOVERN' ) )
            {
            // InternalMlopsDsl.g:8843:2: ( (enumLiteral_0= 'PREPARE' ) | (enumLiteral_1= 'TRAIN' ) | (enumLiteral_2= 'EVAL' ) | (enumLiteral_3= 'PACKAGE' ) | (enumLiteral_4= 'REGISTER' ) | (enumLiteral_5= 'SERVE' ) | (enumLiteral_6= 'MONITOR' ) | (enumLiteral_7= 'VERSION' ) | (enumLiteral_8= 'TRACK' ) | (enumLiteral_9= 'DECIDE' ) | (enumLiteral_10= 'DEPLOY' ) | (enumLiteral_11= 'RETRAIN' ) | (enumLiteral_12= 'GOVERN' ) )
            int alt158=13;
            switch ( input.LA(1) ) {
            case 256:
                {
                alt158=1;
                }
                break;
            case 257:
                {
                alt158=2;
                }
                break;
            case 258:
                {
                alt158=3;
                }
                break;
            case 259:
                {
                alt158=4;
                }
                break;
            case 260:
                {
                alt158=5;
                }
                break;
            case 261:
                {
                alt158=6;
                }
                break;
            case 262:
                {
                alt158=7;
                }
                break;
            case 263:
                {
                alt158=8;
                }
                break;
            case 264:
                {
                alt158=9;
                }
                break;
            case 265:
                {
                alt158=10;
                }
                break;
            case 266:
                {
                alt158=11;
                }
                break;
            case 267:
                {
                alt158=12;
                }
                break;
            case 268:
                {
                alt158=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }

            switch (alt158) {
                case 1 :
                    // InternalMlopsDsl.g:8844:3: (enumLiteral_0= 'PREPARE' )
                    {
                    // InternalMlopsDsl.g:8844:3: (enumLiteral_0= 'PREPARE' )
                    // InternalMlopsDsl.g:8845:4: enumLiteral_0= 'PREPARE'
                    {
                    enumLiteral_0=(Token)match(input,256,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getPREPAREEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTaskTypeAccess().getPREPAREEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8852:3: (enumLiteral_1= 'TRAIN' )
                    {
                    // InternalMlopsDsl.g:8852:3: (enumLiteral_1= 'TRAIN' )
                    // InternalMlopsDsl.g:8853:4: enumLiteral_1= 'TRAIN'
                    {
                    enumLiteral_1=(Token)match(input,257,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getTRAINEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTaskTypeAccess().getTRAINEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:8860:3: (enumLiteral_2= 'EVAL' )
                    {
                    // InternalMlopsDsl.g:8860:3: (enumLiteral_2= 'EVAL' )
                    // InternalMlopsDsl.g:8861:4: enumLiteral_2= 'EVAL'
                    {
                    enumLiteral_2=(Token)match(input,258,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getEVALEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTaskTypeAccess().getEVALEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:8868:3: (enumLiteral_3= 'PACKAGE' )
                    {
                    // InternalMlopsDsl.g:8868:3: (enumLiteral_3= 'PACKAGE' )
                    // InternalMlopsDsl.g:8869:4: enumLiteral_3= 'PACKAGE'
                    {
                    enumLiteral_3=(Token)match(input,259,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getPACKAGEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getTaskTypeAccess().getPACKAGEEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:8876:3: (enumLiteral_4= 'REGISTER' )
                    {
                    // InternalMlopsDsl.g:8876:3: (enumLiteral_4= 'REGISTER' )
                    // InternalMlopsDsl.g:8877:4: enumLiteral_4= 'REGISTER'
                    {
                    enumLiteral_4=(Token)match(input,260,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getREGISTEREnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getTaskTypeAccess().getREGISTEREnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalMlopsDsl.g:8884:3: (enumLiteral_5= 'SERVE' )
                    {
                    // InternalMlopsDsl.g:8884:3: (enumLiteral_5= 'SERVE' )
                    // InternalMlopsDsl.g:8885:4: enumLiteral_5= 'SERVE'
                    {
                    enumLiteral_5=(Token)match(input,261,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getSERVEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getTaskTypeAccess().getSERVEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalMlopsDsl.g:8892:3: (enumLiteral_6= 'MONITOR' )
                    {
                    // InternalMlopsDsl.g:8892:3: (enumLiteral_6= 'MONITOR' )
                    // InternalMlopsDsl.g:8893:4: enumLiteral_6= 'MONITOR'
                    {
                    enumLiteral_6=(Token)match(input,262,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getMONITOREnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getTaskTypeAccess().getMONITOREnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalMlopsDsl.g:8900:3: (enumLiteral_7= 'VERSION' )
                    {
                    // InternalMlopsDsl.g:8900:3: (enumLiteral_7= 'VERSION' )
                    // InternalMlopsDsl.g:8901:4: enumLiteral_7= 'VERSION'
                    {
                    enumLiteral_7=(Token)match(input,263,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getVERSIONEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getTaskTypeAccess().getVERSIONEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalMlopsDsl.g:8908:3: (enumLiteral_8= 'TRACK' )
                    {
                    // InternalMlopsDsl.g:8908:3: (enumLiteral_8= 'TRACK' )
                    // InternalMlopsDsl.g:8909:4: enumLiteral_8= 'TRACK'
                    {
                    enumLiteral_8=(Token)match(input,264,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getTRACKEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getTaskTypeAccess().getTRACKEnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalMlopsDsl.g:8916:3: (enumLiteral_9= 'DECIDE' )
                    {
                    // InternalMlopsDsl.g:8916:3: (enumLiteral_9= 'DECIDE' )
                    // InternalMlopsDsl.g:8917:4: enumLiteral_9= 'DECIDE'
                    {
                    enumLiteral_9=(Token)match(input,265,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getDECIDEEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getTaskTypeAccess().getDECIDEEnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalMlopsDsl.g:8924:3: (enumLiteral_10= 'DEPLOY' )
                    {
                    // InternalMlopsDsl.g:8924:3: (enumLiteral_10= 'DEPLOY' )
                    // InternalMlopsDsl.g:8925:4: enumLiteral_10= 'DEPLOY'
                    {
                    enumLiteral_10=(Token)match(input,266,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getDEPLOYEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getTaskTypeAccess().getDEPLOYEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;
                case 12 :
                    // InternalMlopsDsl.g:8932:3: (enumLiteral_11= 'RETRAIN' )
                    {
                    // InternalMlopsDsl.g:8932:3: (enumLiteral_11= 'RETRAIN' )
                    // InternalMlopsDsl.g:8933:4: enumLiteral_11= 'RETRAIN'
                    {
                    enumLiteral_11=(Token)match(input,267,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getRETRAINEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_11, grammarAccess.getTaskTypeAccess().getRETRAINEnumLiteralDeclaration_11());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalMlopsDsl.g:8940:3: (enumLiteral_12= 'GOVERN' )
                    {
                    // InternalMlopsDsl.g:8940:3: (enumLiteral_12= 'GOVERN' )
                    // InternalMlopsDsl.g:8941:4: enumLiteral_12= 'GOVERN'
                    {
                    enumLiteral_12=(Token)match(input,268,FOLLOW_2); 

                    				current = grammarAccess.getTaskTypeAccess().getGOVERNEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_12, grammarAccess.getTaskTypeAccess().getGOVERNEnumLiteralDeclaration_12());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTaskType"


    // $ANTLR start "ruleTrainMode"
    // InternalMlopsDsl.g:8951:1: ruleTrainMode returns [Enumerator current=null] : ( (enumLiteral_0= 'NOTEBOOK' ) | (enumLiteral_1= 'SCRIPT' ) ) ;
    public final Enumerator ruleTrainMode() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8957:2: ( ( (enumLiteral_0= 'NOTEBOOK' ) | (enumLiteral_1= 'SCRIPT' ) ) )
            // InternalMlopsDsl.g:8958:2: ( (enumLiteral_0= 'NOTEBOOK' ) | (enumLiteral_1= 'SCRIPT' ) )
            {
            // InternalMlopsDsl.g:8958:2: ( (enumLiteral_0= 'NOTEBOOK' ) | (enumLiteral_1= 'SCRIPT' ) )
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==269) ) {
                alt159=1;
            }
            else if ( (LA159_0==270) ) {
                alt159=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 159, 0, input);

                throw nvae;
            }
            switch (alt159) {
                case 1 :
                    // InternalMlopsDsl.g:8959:3: (enumLiteral_0= 'NOTEBOOK' )
                    {
                    // InternalMlopsDsl.g:8959:3: (enumLiteral_0= 'NOTEBOOK' )
                    // InternalMlopsDsl.g:8960:4: enumLiteral_0= 'NOTEBOOK'
                    {
                    enumLiteral_0=(Token)match(input,269,FOLLOW_2); 

                    				current = grammarAccess.getTrainModeAccess().getNOTEBOOKEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTrainModeAccess().getNOTEBOOKEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8967:3: (enumLiteral_1= 'SCRIPT' )
                    {
                    // InternalMlopsDsl.g:8967:3: (enumLiteral_1= 'SCRIPT' )
                    // InternalMlopsDsl.g:8968:4: enumLiteral_1= 'SCRIPT'
                    {
                    enumLiteral_1=(Token)match(input,270,FOLLOW_2); 

                    				current = grammarAccess.getTrainModeAccess().getSCRIPTEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTrainModeAccess().getSCRIPTEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrainMode"


    // $ANTLR start "ruleCITarget"
    // InternalMlopsDsl.g:8978:1: ruleCITarget returns [Enumerator current=null] : ( (enumLiteral_0= 'GITHUB' ) | (enumLiteral_1= 'GITLAB' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'JENKINS' ) ) ;
    public final Enumerator ruleCITarget() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:8984:2: ( ( (enumLiteral_0= 'GITHUB' ) | (enumLiteral_1= 'GITLAB' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'JENKINS' ) ) )
            // InternalMlopsDsl.g:8985:2: ( (enumLiteral_0= 'GITHUB' ) | (enumLiteral_1= 'GITLAB' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'JENKINS' ) )
            {
            // InternalMlopsDsl.g:8985:2: ( (enumLiteral_0= 'GITHUB' ) | (enumLiteral_1= 'GITLAB' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'JENKINS' ) )
            int alt160=4;
            switch ( input.LA(1) ) {
            case 271:
                {
                alt160=1;
                }
                break;
            case 272:
                {
                alt160=2;
                }
                break;
            case 273:
                {
                alt160=3;
                }
                break;
            case 274:
                {
                alt160=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 160, 0, input);

                throw nvae;
            }

            switch (alt160) {
                case 1 :
                    // InternalMlopsDsl.g:8986:3: (enumLiteral_0= 'GITHUB' )
                    {
                    // InternalMlopsDsl.g:8986:3: (enumLiteral_0= 'GITHUB' )
                    // InternalMlopsDsl.g:8987:4: enumLiteral_0= 'GITHUB'
                    {
                    enumLiteral_0=(Token)match(input,271,FOLLOW_2); 

                    				current = grammarAccess.getCITargetAccess().getGITHUBEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCITargetAccess().getGITHUBEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:8994:3: (enumLiteral_1= 'GITLAB' )
                    {
                    // InternalMlopsDsl.g:8994:3: (enumLiteral_1= 'GITLAB' )
                    // InternalMlopsDsl.g:8995:4: enumLiteral_1= 'GITLAB'
                    {
                    enumLiteral_1=(Token)match(input,272,FOLLOW_2); 

                    				current = grammarAccess.getCITargetAccess().getGITLABEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCITargetAccess().getGITLABEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9002:3: (enumLiteral_2= 'AZURE' )
                    {
                    // InternalMlopsDsl.g:9002:3: (enumLiteral_2= 'AZURE' )
                    // InternalMlopsDsl.g:9003:4: enumLiteral_2= 'AZURE'
                    {
                    enumLiteral_2=(Token)match(input,273,FOLLOW_2); 

                    				current = grammarAccess.getCITargetAccess().getAZUREEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getCITargetAccess().getAZUREEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9010:3: (enumLiteral_3= 'JENKINS' )
                    {
                    // InternalMlopsDsl.g:9010:3: (enumLiteral_3= 'JENKINS' )
                    // InternalMlopsDsl.g:9011:4: enumLiteral_3= 'JENKINS'
                    {
                    enumLiteral_3=(Token)match(input,274,FOLLOW_2); 

                    				current = grammarAccess.getCITargetAccess().getJENKINSEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getCITargetAccess().getJENKINSEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCITarget"


    // $ANTLR start "ruleCompOp"
    // InternalMlopsDsl.g:9021:1: ruleCompOp returns [Enumerator current=null] : ( (enumLiteral_0= 'GT' ) | (enumLiteral_1= 'GE' ) | (enumLiteral_2= 'LT' ) | (enumLiteral_3= 'LE' ) | (enumLiteral_4= 'EQ' ) ) ;
    public final Enumerator ruleCompOp() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9027:2: ( ( (enumLiteral_0= 'GT' ) | (enumLiteral_1= 'GE' ) | (enumLiteral_2= 'LT' ) | (enumLiteral_3= 'LE' ) | (enumLiteral_4= 'EQ' ) ) )
            // InternalMlopsDsl.g:9028:2: ( (enumLiteral_0= 'GT' ) | (enumLiteral_1= 'GE' ) | (enumLiteral_2= 'LT' ) | (enumLiteral_3= 'LE' ) | (enumLiteral_4= 'EQ' ) )
            {
            // InternalMlopsDsl.g:9028:2: ( (enumLiteral_0= 'GT' ) | (enumLiteral_1= 'GE' ) | (enumLiteral_2= 'LT' ) | (enumLiteral_3= 'LE' ) | (enumLiteral_4= 'EQ' ) )
            int alt161=5;
            switch ( input.LA(1) ) {
            case 275:
                {
                alt161=1;
                }
                break;
            case 276:
                {
                alt161=2;
                }
                break;
            case 277:
                {
                alt161=3;
                }
                break;
            case 278:
                {
                alt161=4;
                }
                break;
            case 279:
                {
                alt161=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }

            switch (alt161) {
                case 1 :
                    // InternalMlopsDsl.g:9029:3: (enumLiteral_0= 'GT' )
                    {
                    // InternalMlopsDsl.g:9029:3: (enumLiteral_0= 'GT' )
                    // InternalMlopsDsl.g:9030:4: enumLiteral_0= 'GT'
                    {
                    enumLiteral_0=(Token)match(input,275,FOLLOW_2); 

                    				current = grammarAccess.getCompOpAccess().getGTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCompOpAccess().getGTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9037:3: (enumLiteral_1= 'GE' )
                    {
                    // InternalMlopsDsl.g:9037:3: (enumLiteral_1= 'GE' )
                    // InternalMlopsDsl.g:9038:4: enumLiteral_1= 'GE'
                    {
                    enumLiteral_1=(Token)match(input,276,FOLLOW_2); 

                    				current = grammarAccess.getCompOpAccess().getGEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCompOpAccess().getGEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9045:3: (enumLiteral_2= 'LT' )
                    {
                    // InternalMlopsDsl.g:9045:3: (enumLiteral_2= 'LT' )
                    // InternalMlopsDsl.g:9046:4: enumLiteral_2= 'LT'
                    {
                    enumLiteral_2=(Token)match(input,277,FOLLOW_2); 

                    				current = grammarAccess.getCompOpAccess().getLTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getCompOpAccess().getLTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9053:3: (enumLiteral_3= 'LE' )
                    {
                    // InternalMlopsDsl.g:9053:3: (enumLiteral_3= 'LE' )
                    // InternalMlopsDsl.g:9054:4: enumLiteral_3= 'LE'
                    {
                    enumLiteral_3=(Token)match(input,278,FOLLOW_2); 

                    				current = grammarAccess.getCompOpAccess().getLEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getCompOpAccess().getLEEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9061:3: (enumLiteral_4= 'EQ' )
                    {
                    // InternalMlopsDsl.g:9061:3: (enumLiteral_4= 'EQ' )
                    // InternalMlopsDsl.g:9062:4: enumLiteral_4= 'EQ'
                    {
                    enumLiteral_4=(Token)match(input,279,FOLLOW_2); 

                    				current = grammarAccess.getCompOpAccess().getEQEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getCompOpAccess().getEQEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCompOp"


    // $ANTLR start "ruleRegistryStage"
    // InternalMlopsDsl.g:9072:1: ruleRegistryStage returns [Enumerator current=null] : ( (enumLiteral_0= 'STAGING' ) | (enumLiteral_1= 'PROD' ) | (enumLiteral_2= 'ARCHIVED' ) ) ;
    public final Enumerator ruleRegistryStage() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9078:2: ( ( (enumLiteral_0= 'STAGING' ) | (enumLiteral_1= 'PROD' ) | (enumLiteral_2= 'ARCHIVED' ) ) )
            // InternalMlopsDsl.g:9079:2: ( (enumLiteral_0= 'STAGING' ) | (enumLiteral_1= 'PROD' ) | (enumLiteral_2= 'ARCHIVED' ) )
            {
            // InternalMlopsDsl.g:9079:2: ( (enumLiteral_0= 'STAGING' ) | (enumLiteral_1= 'PROD' ) | (enumLiteral_2= 'ARCHIVED' ) )
            int alt162=3;
            switch ( input.LA(1) ) {
            case 280:
                {
                alt162=1;
                }
                break;
            case 281:
                {
                alt162=2;
                }
                break;
            case 282:
                {
                alt162=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }

            switch (alt162) {
                case 1 :
                    // InternalMlopsDsl.g:9080:3: (enumLiteral_0= 'STAGING' )
                    {
                    // InternalMlopsDsl.g:9080:3: (enumLiteral_0= 'STAGING' )
                    // InternalMlopsDsl.g:9081:4: enumLiteral_0= 'STAGING'
                    {
                    enumLiteral_0=(Token)match(input,280,FOLLOW_2); 

                    				current = grammarAccess.getRegistryStageAccess().getSTAGINGEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getRegistryStageAccess().getSTAGINGEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9088:3: (enumLiteral_1= 'PROD' )
                    {
                    // InternalMlopsDsl.g:9088:3: (enumLiteral_1= 'PROD' )
                    // InternalMlopsDsl.g:9089:4: enumLiteral_1= 'PROD'
                    {
                    enumLiteral_1=(Token)match(input,281,FOLLOW_2); 

                    				current = grammarAccess.getRegistryStageAccess().getPRODEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getRegistryStageAccess().getPRODEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9096:3: (enumLiteral_2= 'ARCHIVED' )
                    {
                    // InternalMlopsDsl.g:9096:3: (enumLiteral_2= 'ARCHIVED' )
                    // InternalMlopsDsl.g:9097:4: enumLiteral_2= 'ARCHIVED'
                    {
                    enumLiteral_2=(Token)match(input,282,FOLLOW_2); 

                    				current = grammarAccess.getRegistryStageAccess().getARCHIVEDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getRegistryStageAccess().getARCHIVEDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegistryStage"


    // $ANTLR start "ruleCloudProvider"
    // InternalMlopsDsl.g:9107:1: ruleCloudProvider returns [Enumerator current=null] : ( (enumLiteral_0= 'AWS' ) | (enumLiteral_1= 'GCP' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'ON_PREM' ) | (enumLiteral_4= 'VPS' ) ) ;
    public final Enumerator ruleCloudProvider() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9113:2: ( ( (enumLiteral_0= 'AWS' ) | (enumLiteral_1= 'GCP' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'ON_PREM' ) | (enumLiteral_4= 'VPS' ) ) )
            // InternalMlopsDsl.g:9114:2: ( (enumLiteral_0= 'AWS' ) | (enumLiteral_1= 'GCP' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'ON_PREM' ) | (enumLiteral_4= 'VPS' ) )
            {
            // InternalMlopsDsl.g:9114:2: ( (enumLiteral_0= 'AWS' ) | (enumLiteral_1= 'GCP' ) | (enumLiteral_2= 'AZURE' ) | (enumLiteral_3= 'ON_PREM' ) | (enumLiteral_4= 'VPS' ) )
            int alt163=5;
            switch ( input.LA(1) ) {
            case 283:
                {
                alt163=1;
                }
                break;
            case 284:
                {
                alt163=2;
                }
                break;
            case 273:
                {
                alt163=3;
                }
                break;
            case 285:
                {
                alt163=4;
                }
                break;
            case 286:
                {
                alt163=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 163, 0, input);

                throw nvae;
            }

            switch (alt163) {
                case 1 :
                    // InternalMlopsDsl.g:9115:3: (enumLiteral_0= 'AWS' )
                    {
                    // InternalMlopsDsl.g:9115:3: (enumLiteral_0= 'AWS' )
                    // InternalMlopsDsl.g:9116:4: enumLiteral_0= 'AWS'
                    {
                    enumLiteral_0=(Token)match(input,283,FOLLOW_2); 

                    				current = grammarAccess.getCloudProviderAccess().getAWSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getCloudProviderAccess().getAWSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9123:3: (enumLiteral_1= 'GCP' )
                    {
                    // InternalMlopsDsl.g:9123:3: (enumLiteral_1= 'GCP' )
                    // InternalMlopsDsl.g:9124:4: enumLiteral_1= 'GCP'
                    {
                    enumLiteral_1=(Token)match(input,284,FOLLOW_2); 

                    				current = grammarAccess.getCloudProviderAccess().getGCPEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getCloudProviderAccess().getGCPEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9131:3: (enumLiteral_2= 'AZURE' )
                    {
                    // InternalMlopsDsl.g:9131:3: (enumLiteral_2= 'AZURE' )
                    // InternalMlopsDsl.g:9132:4: enumLiteral_2= 'AZURE'
                    {
                    enumLiteral_2=(Token)match(input,273,FOLLOW_2); 

                    				current = grammarAccess.getCloudProviderAccess().getAZUREEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getCloudProviderAccess().getAZUREEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9139:3: (enumLiteral_3= 'ON_PREM' )
                    {
                    // InternalMlopsDsl.g:9139:3: (enumLiteral_3= 'ON_PREM' )
                    // InternalMlopsDsl.g:9140:4: enumLiteral_3= 'ON_PREM'
                    {
                    enumLiteral_3=(Token)match(input,285,FOLLOW_2); 

                    				current = grammarAccess.getCloudProviderAccess().getON_PREMEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getCloudProviderAccess().getON_PREMEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9147:3: (enumLiteral_4= 'VPS' )
                    {
                    // InternalMlopsDsl.g:9147:3: (enumLiteral_4= 'VPS' )
                    // InternalMlopsDsl.g:9148:4: enumLiteral_4= 'VPS'
                    {
                    enumLiteral_4=(Token)match(input,286,FOLLOW_2); 

                    				current = grammarAccess.getCloudProviderAccess().getVPSEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getCloudProviderAccess().getVPSEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleCloudProvider"


    // $ANTLR start "ruleOrchestrator"
    // InternalMlopsDsl.g:9158:1: ruleOrchestrator returns [Enumerator current=null] : ( (enumLiteral_0= 'KUBERNETES' ) | (enumLiteral_1= 'ECS' ) | (enumLiteral_2= 'VM_POOL' ) | (enumLiteral_3= 'SERVERLESS_FN' ) ) ;
    public final Enumerator ruleOrchestrator() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9164:2: ( ( (enumLiteral_0= 'KUBERNETES' ) | (enumLiteral_1= 'ECS' ) | (enumLiteral_2= 'VM_POOL' ) | (enumLiteral_3= 'SERVERLESS_FN' ) ) )
            // InternalMlopsDsl.g:9165:2: ( (enumLiteral_0= 'KUBERNETES' ) | (enumLiteral_1= 'ECS' ) | (enumLiteral_2= 'VM_POOL' ) | (enumLiteral_3= 'SERVERLESS_FN' ) )
            {
            // InternalMlopsDsl.g:9165:2: ( (enumLiteral_0= 'KUBERNETES' ) | (enumLiteral_1= 'ECS' ) | (enumLiteral_2= 'VM_POOL' ) | (enumLiteral_3= 'SERVERLESS_FN' ) )
            int alt164=4;
            switch ( input.LA(1) ) {
            case 287:
                {
                alt164=1;
                }
                break;
            case 288:
                {
                alt164=2;
                }
                break;
            case 289:
                {
                alt164=3;
                }
                break;
            case 290:
                {
                alt164=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 164, 0, input);

                throw nvae;
            }

            switch (alt164) {
                case 1 :
                    // InternalMlopsDsl.g:9166:3: (enumLiteral_0= 'KUBERNETES' )
                    {
                    // InternalMlopsDsl.g:9166:3: (enumLiteral_0= 'KUBERNETES' )
                    // InternalMlopsDsl.g:9167:4: enumLiteral_0= 'KUBERNETES'
                    {
                    enumLiteral_0=(Token)match(input,287,FOLLOW_2); 

                    				current = grammarAccess.getOrchestratorAccess().getKUBERNETESEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getOrchestratorAccess().getKUBERNETESEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9174:3: (enumLiteral_1= 'ECS' )
                    {
                    // InternalMlopsDsl.g:9174:3: (enumLiteral_1= 'ECS' )
                    // InternalMlopsDsl.g:9175:4: enumLiteral_1= 'ECS'
                    {
                    enumLiteral_1=(Token)match(input,288,FOLLOW_2); 

                    				current = grammarAccess.getOrchestratorAccess().getECSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getOrchestratorAccess().getECSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9182:3: (enumLiteral_2= 'VM_POOL' )
                    {
                    // InternalMlopsDsl.g:9182:3: (enumLiteral_2= 'VM_POOL' )
                    // InternalMlopsDsl.g:9183:4: enumLiteral_2= 'VM_POOL'
                    {
                    enumLiteral_2=(Token)match(input,289,FOLLOW_2); 

                    				current = grammarAccess.getOrchestratorAccess().getVM_POOLEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getOrchestratorAccess().getVM_POOLEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9190:3: (enumLiteral_3= 'SERVERLESS_FN' )
                    {
                    // InternalMlopsDsl.g:9190:3: (enumLiteral_3= 'SERVERLESS_FN' )
                    // InternalMlopsDsl.g:9191:4: enumLiteral_3= 'SERVERLESS_FN'
                    {
                    enumLiteral_3=(Token)match(input,290,FOLLOW_2); 

                    				current = grammarAccess.getOrchestratorAccess().getSERVERLESS_FNEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getOrchestratorAccess().getSERVERLESS_FNEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleOrchestrator"


    // $ANTLR start "ruleStorageClass"
    // InternalMlopsDsl.g:9201:1: ruleStorageClass returns [Enumerator current=null] : ( (enumLiteral_0= 'OBJECT_STORE' ) | (enumLiteral_1= 'BLOCK' ) | (enumLiteral_2= 'FILESYSTEM' ) ) ;
    public final Enumerator ruleStorageClass() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9207:2: ( ( (enumLiteral_0= 'OBJECT_STORE' ) | (enumLiteral_1= 'BLOCK' ) | (enumLiteral_2= 'FILESYSTEM' ) ) )
            // InternalMlopsDsl.g:9208:2: ( (enumLiteral_0= 'OBJECT_STORE' ) | (enumLiteral_1= 'BLOCK' ) | (enumLiteral_2= 'FILESYSTEM' ) )
            {
            // InternalMlopsDsl.g:9208:2: ( (enumLiteral_0= 'OBJECT_STORE' ) | (enumLiteral_1= 'BLOCK' ) | (enumLiteral_2= 'FILESYSTEM' ) )
            int alt165=3;
            switch ( input.LA(1) ) {
            case 291:
                {
                alt165=1;
                }
                break;
            case 292:
                {
                alt165=2;
                }
                break;
            case 293:
                {
                alt165=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 165, 0, input);

                throw nvae;
            }

            switch (alt165) {
                case 1 :
                    // InternalMlopsDsl.g:9209:3: (enumLiteral_0= 'OBJECT_STORE' )
                    {
                    // InternalMlopsDsl.g:9209:3: (enumLiteral_0= 'OBJECT_STORE' )
                    // InternalMlopsDsl.g:9210:4: enumLiteral_0= 'OBJECT_STORE'
                    {
                    enumLiteral_0=(Token)match(input,291,FOLLOW_2); 

                    				current = grammarAccess.getStorageClassAccess().getOBJECT_STOREEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getStorageClassAccess().getOBJECT_STOREEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9217:3: (enumLiteral_1= 'BLOCK' )
                    {
                    // InternalMlopsDsl.g:9217:3: (enumLiteral_1= 'BLOCK' )
                    // InternalMlopsDsl.g:9218:4: enumLiteral_1= 'BLOCK'
                    {
                    enumLiteral_1=(Token)match(input,292,FOLLOW_2); 

                    				current = grammarAccess.getStorageClassAccess().getBLOCKEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getStorageClassAccess().getBLOCKEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9225:3: (enumLiteral_2= 'FILESYSTEM' )
                    {
                    // InternalMlopsDsl.g:9225:3: (enumLiteral_2= 'FILESYSTEM' )
                    // InternalMlopsDsl.g:9226:4: enumLiteral_2= 'FILESYSTEM'
                    {
                    enumLiteral_2=(Token)match(input,293,FOLLOW_2); 

                    				current = grammarAccess.getStorageClassAccess().getFILESYSTEMEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getStorageClassAccess().getFILESYSTEMEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleStorageClass"


    // $ANTLR start "ruleExposureKind"
    // InternalMlopsDsl.g:9236:1: ruleExposureKind returns [Enumerator current=null] : ( (enumLiteral_0= 'PRIVATE' ) | (enumLiteral_1= 'PUBLIC' ) | (enumLiteral_2= 'INTERNAL_ONLY' ) ) ;
    public final Enumerator ruleExposureKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9242:2: ( ( (enumLiteral_0= 'PRIVATE' ) | (enumLiteral_1= 'PUBLIC' ) | (enumLiteral_2= 'INTERNAL_ONLY' ) ) )
            // InternalMlopsDsl.g:9243:2: ( (enumLiteral_0= 'PRIVATE' ) | (enumLiteral_1= 'PUBLIC' ) | (enumLiteral_2= 'INTERNAL_ONLY' ) )
            {
            // InternalMlopsDsl.g:9243:2: ( (enumLiteral_0= 'PRIVATE' ) | (enumLiteral_1= 'PUBLIC' ) | (enumLiteral_2= 'INTERNAL_ONLY' ) )
            int alt166=3;
            switch ( input.LA(1) ) {
            case 294:
                {
                alt166=1;
                }
                break;
            case 295:
                {
                alt166=2;
                }
                break;
            case 296:
                {
                alt166=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 166, 0, input);

                throw nvae;
            }

            switch (alt166) {
                case 1 :
                    // InternalMlopsDsl.g:9244:3: (enumLiteral_0= 'PRIVATE' )
                    {
                    // InternalMlopsDsl.g:9244:3: (enumLiteral_0= 'PRIVATE' )
                    // InternalMlopsDsl.g:9245:4: enumLiteral_0= 'PRIVATE'
                    {
                    enumLiteral_0=(Token)match(input,294,FOLLOW_2); 

                    				current = grammarAccess.getExposureKindAccess().getPRIVATEEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getExposureKindAccess().getPRIVATEEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9252:3: (enumLiteral_1= 'PUBLIC' )
                    {
                    // InternalMlopsDsl.g:9252:3: (enumLiteral_1= 'PUBLIC' )
                    // InternalMlopsDsl.g:9253:4: enumLiteral_1= 'PUBLIC'
                    {
                    enumLiteral_1=(Token)match(input,295,FOLLOW_2); 

                    				current = grammarAccess.getExposureKindAccess().getPUBLICEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getExposureKindAccess().getPUBLICEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9260:3: (enumLiteral_2= 'INTERNAL_ONLY' )
                    {
                    // InternalMlopsDsl.g:9260:3: (enumLiteral_2= 'INTERNAL_ONLY' )
                    // InternalMlopsDsl.g:9261:4: enumLiteral_2= 'INTERNAL_ONLY'
                    {
                    enumLiteral_2=(Token)match(input,296,FOLLOW_2); 

                    				current = grammarAccess.getExposureKindAccess().getINTERNAL_ONLYEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getExposureKindAccess().getINTERNAL_ONLYEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleExposureKind"


    // $ANTLR start "ruleSecretBackend"
    // InternalMlopsDsl.g:9271:1: ruleSecretBackend returns [Enumerator current=null] : ( (enumLiteral_0= 'AWS_SECRETS_MANAGER' ) | (enumLiteral_1= 'GCP_SECRET_MANAGER' ) | (enumLiteral_2= 'VAULT' ) | (enumLiteral_3= 'K8S_SECRET' ) | (enumLiteral_4= 'FILE_ENV' ) ) ;
    public final Enumerator ruleSecretBackend() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9277:2: ( ( (enumLiteral_0= 'AWS_SECRETS_MANAGER' ) | (enumLiteral_1= 'GCP_SECRET_MANAGER' ) | (enumLiteral_2= 'VAULT' ) | (enumLiteral_3= 'K8S_SECRET' ) | (enumLiteral_4= 'FILE_ENV' ) ) )
            // InternalMlopsDsl.g:9278:2: ( (enumLiteral_0= 'AWS_SECRETS_MANAGER' ) | (enumLiteral_1= 'GCP_SECRET_MANAGER' ) | (enumLiteral_2= 'VAULT' ) | (enumLiteral_3= 'K8S_SECRET' ) | (enumLiteral_4= 'FILE_ENV' ) )
            {
            // InternalMlopsDsl.g:9278:2: ( (enumLiteral_0= 'AWS_SECRETS_MANAGER' ) | (enumLiteral_1= 'GCP_SECRET_MANAGER' ) | (enumLiteral_2= 'VAULT' ) | (enumLiteral_3= 'K8S_SECRET' ) | (enumLiteral_4= 'FILE_ENV' ) )
            int alt167=5;
            switch ( input.LA(1) ) {
            case 297:
                {
                alt167=1;
                }
                break;
            case 298:
                {
                alt167=2;
                }
                break;
            case 299:
                {
                alt167=3;
                }
                break;
            case 300:
                {
                alt167=4;
                }
                break;
            case 301:
                {
                alt167=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 167, 0, input);

                throw nvae;
            }

            switch (alt167) {
                case 1 :
                    // InternalMlopsDsl.g:9279:3: (enumLiteral_0= 'AWS_SECRETS_MANAGER' )
                    {
                    // InternalMlopsDsl.g:9279:3: (enumLiteral_0= 'AWS_SECRETS_MANAGER' )
                    // InternalMlopsDsl.g:9280:4: enumLiteral_0= 'AWS_SECRETS_MANAGER'
                    {
                    enumLiteral_0=(Token)match(input,297,FOLLOW_2); 

                    				current = grammarAccess.getSecretBackendAccess().getAWS_SECRETS_MANAGEREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getSecretBackendAccess().getAWS_SECRETS_MANAGEREnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9287:3: (enumLiteral_1= 'GCP_SECRET_MANAGER' )
                    {
                    // InternalMlopsDsl.g:9287:3: (enumLiteral_1= 'GCP_SECRET_MANAGER' )
                    // InternalMlopsDsl.g:9288:4: enumLiteral_1= 'GCP_SECRET_MANAGER'
                    {
                    enumLiteral_1=(Token)match(input,298,FOLLOW_2); 

                    				current = grammarAccess.getSecretBackendAccess().getGCP_SECRET_MANAGEREnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getSecretBackendAccess().getGCP_SECRET_MANAGEREnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9295:3: (enumLiteral_2= 'VAULT' )
                    {
                    // InternalMlopsDsl.g:9295:3: (enumLiteral_2= 'VAULT' )
                    // InternalMlopsDsl.g:9296:4: enumLiteral_2= 'VAULT'
                    {
                    enumLiteral_2=(Token)match(input,299,FOLLOW_2); 

                    				current = grammarAccess.getSecretBackendAccess().getVAULTEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getSecretBackendAccess().getVAULTEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9303:3: (enumLiteral_3= 'K8S_SECRET' )
                    {
                    // InternalMlopsDsl.g:9303:3: (enumLiteral_3= 'K8S_SECRET' )
                    // InternalMlopsDsl.g:9304:4: enumLiteral_3= 'K8S_SECRET'
                    {
                    enumLiteral_3=(Token)match(input,300,FOLLOW_2); 

                    				current = grammarAccess.getSecretBackendAccess().getK8S_SECRETEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getSecretBackendAccess().getK8S_SECRETEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9311:3: (enumLiteral_4= 'FILE_ENV' )
                    {
                    // InternalMlopsDsl.g:9311:3: (enumLiteral_4= 'FILE_ENV' )
                    // InternalMlopsDsl.g:9312:4: enumLiteral_4= 'FILE_ENV'
                    {
                    enumLiteral_4=(Token)match(input,301,FOLLOW_2); 

                    				current = grammarAccess.getSecretBackendAccess().getFILE_ENVEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getSecretBackendAccess().getFILE_ENVEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleSecretBackend"


    // $ANTLR start "ruleObservabilityStack"
    // InternalMlopsDsl.g:9322:1: ruleObservabilityStack returns [Enumerator current=null] : ( (enumLiteral_0= 'PROMETHEUS' ) | (enumLiteral_1= 'CLOUDWATCH' ) | (enumLiteral_2= 'STACKDRIVER' ) | (enumLiteral_3= 'DATADOG' ) | (enumLiteral_4= 'ELK' ) ) ;
    public final Enumerator ruleObservabilityStack() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9328:2: ( ( (enumLiteral_0= 'PROMETHEUS' ) | (enumLiteral_1= 'CLOUDWATCH' ) | (enumLiteral_2= 'STACKDRIVER' ) | (enumLiteral_3= 'DATADOG' ) | (enumLiteral_4= 'ELK' ) ) )
            // InternalMlopsDsl.g:9329:2: ( (enumLiteral_0= 'PROMETHEUS' ) | (enumLiteral_1= 'CLOUDWATCH' ) | (enumLiteral_2= 'STACKDRIVER' ) | (enumLiteral_3= 'DATADOG' ) | (enumLiteral_4= 'ELK' ) )
            {
            // InternalMlopsDsl.g:9329:2: ( (enumLiteral_0= 'PROMETHEUS' ) | (enumLiteral_1= 'CLOUDWATCH' ) | (enumLiteral_2= 'STACKDRIVER' ) | (enumLiteral_3= 'DATADOG' ) | (enumLiteral_4= 'ELK' ) )
            int alt168=5;
            switch ( input.LA(1) ) {
            case 302:
                {
                alt168=1;
                }
                break;
            case 303:
                {
                alt168=2;
                }
                break;
            case 304:
                {
                alt168=3;
                }
                break;
            case 305:
                {
                alt168=4;
                }
                break;
            case 306:
                {
                alt168=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 168, 0, input);

                throw nvae;
            }

            switch (alt168) {
                case 1 :
                    // InternalMlopsDsl.g:9330:3: (enumLiteral_0= 'PROMETHEUS' )
                    {
                    // InternalMlopsDsl.g:9330:3: (enumLiteral_0= 'PROMETHEUS' )
                    // InternalMlopsDsl.g:9331:4: enumLiteral_0= 'PROMETHEUS'
                    {
                    enumLiteral_0=(Token)match(input,302,FOLLOW_2); 

                    				current = grammarAccess.getObservabilityStackAccess().getPROMETHEUSEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getObservabilityStackAccess().getPROMETHEUSEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9338:3: (enumLiteral_1= 'CLOUDWATCH' )
                    {
                    // InternalMlopsDsl.g:9338:3: (enumLiteral_1= 'CLOUDWATCH' )
                    // InternalMlopsDsl.g:9339:4: enumLiteral_1= 'CLOUDWATCH'
                    {
                    enumLiteral_1=(Token)match(input,303,FOLLOW_2); 

                    				current = grammarAccess.getObservabilityStackAccess().getCLOUDWATCHEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getObservabilityStackAccess().getCLOUDWATCHEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9346:3: (enumLiteral_2= 'STACKDRIVER' )
                    {
                    // InternalMlopsDsl.g:9346:3: (enumLiteral_2= 'STACKDRIVER' )
                    // InternalMlopsDsl.g:9347:4: enumLiteral_2= 'STACKDRIVER'
                    {
                    enumLiteral_2=(Token)match(input,304,FOLLOW_2); 

                    				current = grammarAccess.getObservabilityStackAccess().getSTACKDRIVEREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getObservabilityStackAccess().getSTACKDRIVEREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9354:3: (enumLiteral_3= 'DATADOG' )
                    {
                    // InternalMlopsDsl.g:9354:3: (enumLiteral_3= 'DATADOG' )
                    // InternalMlopsDsl.g:9355:4: enumLiteral_3= 'DATADOG'
                    {
                    enumLiteral_3=(Token)match(input,305,FOLLOW_2); 

                    				current = grammarAccess.getObservabilityStackAccess().getDATADOGEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getObservabilityStackAccess().getDATADOGEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9362:3: (enumLiteral_4= 'ELK' )
                    {
                    // InternalMlopsDsl.g:9362:3: (enumLiteral_4= 'ELK' )
                    // InternalMlopsDsl.g:9363:4: enumLiteral_4= 'ELK'
                    {
                    enumLiteral_4=(Token)match(input,306,FOLLOW_2); 

                    				current = grammarAccess.getObservabilityStackAccess().getELKEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getObservabilityStackAccess().getELKEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleObservabilityStack"


    // $ANTLR start "ruleDataSource"
    // InternalMlopsDsl.g:9373:1: ruleDataSource returns [Enumerator current=null] : ( (enumLiteral_0= 'CSV' ) | (enumLiteral_1= 'PARQUET' ) | (enumLiteral_2= 'JSONL' ) | (enumLiteral_3= 'IMAGE_FOLDER' ) | (enumLiteral_4= 'VIDEO_FOLDER' ) | (enumLiteral_5= 'AUDIO_FOLDER' ) | (enumLiteral_6= 'TEXT_CORPUS' ) | (enumLiteral_7= 'HUGGINGFACE_DATASET' ) | (enumLiteral_8= 'S3' ) | (enumLiteral_9= 'GCS' ) | (enumLiteral_10= 'AZURE_BLOB' ) | (enumLiteral_11= 'BIGQUERY' ) | (enumLiteral_12= 'SQL' ) | (enumLiteral_13= 'NOSQL' ) | (enumLiteral_14= 'KAFKA' ) | (enumLiteral_15= 'REST' ) | (enumLiteral_16= 'GRAPH' ) ) ;
    public final Enumerator ruleDataSource() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;
        Token enumLiteral_11=null;
        Token enumLiteral_12=null;
        Token enumLiteral_13=null;
        Token enumLiteral_14=null;
        Token enumLiteral_15=null;
        Token enumLiteral_16=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9379:2: ( ( (enumLiteral_0= 'CSV' ) | (enumLiteral_1= 'PARQUET' ) | (enumLiteral_2= 'JSONL' ) | (enumLiteral_3= 'IMAGE_FOLDER' ) | (enumLiteral_4= 'VIDEO_FOLDER' ) | (enumLiteral_5= 'AUDIO_FOLDER' ) | (enumLiteral_6= 'TEXT_CORPUS' ) | (enumLiteral_7= 'HUGGINGFACE_DATASET' ) | (enumLiteral_8= 'S3' ) | (enumLiteral_9= 'GCS' ) | (enumLiteral_10= 'AZURE_BLOB' ) | (enumLiteral_11= 'BIGQUERY' ) | (enumLiteral_12= 'SQL' ) | (enumLiteral_13= 'NOSQL' ) | (enumLiteral_14= 'KAFKA' ) | (enumLiteral_15= 'REST' ) | (enumLiteral_16= 'GRAPH' ) ) )
            // InternalMlopsDsl.g:9380:2: ( (enumLiteral_0= 'CSV' ) | (enumLiteral_1= 'PARQUET' ) | (enumLiteral_2= 'JSONL' ) | (enumLiteral_3= 'IMAGE_FOLDER' ) | (enumLiteral_4= 'VIDEO_FOLDER' ) | (enumLiteral_5= 'AUDIO_FOLDER' ) | (enumLiteral_6= 'TEXT_CORPUS' ) | (enumLiteral_7= 'HUGGINGFACE_DATASET' ) | (enumLiteral_8= 'S3' ) | (enumLiteral_9= 'GCS' ) | (enumLiteral_10= 'AZURE_BLOB' ) | (enumLiteral_11= 'BIGQUERY' ) | (enumLiteral_12= 'SQL' ) | (enumLiteral_13= 'NOSQL' ) | (enumLiteral_14= 'KAFKA' ) | (enumLiteral_15= 'REST' ) | (enumLiteral_16= 'GRAPH' ) )
            {
            // InternalMlopsDsl.g:9380:2: ( (enumLiteral_0= 'CSV' ) | (enumLiteral_1= 'PARQUET' ) | (enumLiteral_2= 'JSONL' ) | (enumLiteral_3= 'IMAGE_FOLDER' ) | (enumLiteral_4= 'VIDEO_FOLDER' ) | (enumLiteral_5= 'AUDIO_FOLDER' ) | (enumLiteral_6= 'TEXT_CORPUS' ) | (enumLiteral_7= 'HUGGINGFACE_DATASET' ) | (enumLiteral_8= 'S3' ) | (enumLiteral_9= 'GCS' ) | (enumLiteral_10= 'AZURE_BLOB' ) | (enumLiteral_11= 'BIGQUERY' ) | (enumLiteral_12= 'SQL' ) | (enumLiteral_13= 'NOSQL' ) | (enumLiteral_14= 'KAFKA' ) | (enumLiteral_15= 'REST' ) | (enumLiteral_16= 'GRAPH' ) )
            int alt169=17;
            switch ( input.LA(1) ) {
            case 307:
                {
                alt169=1;
                }
                break;
            case 308:
                {
                alt169=2;
                }
                break;
            case 309:
                {
                alt169=3;
                }
                break;
            case 310:
                {
                alt169=4;
                }
                break;
            case 311:
                {
                alt169=5;
                }
                break;
            case 312:
                {
                alt169=6;
                }
                break;
            case 313:
                {
                alt169=7;
                }
                break;
            case 314:
                {
                alt169=8;
                }
                break;
            case 315:
                {
                alt169=9;
                }
                break;
            case 316:
                {
                alt169=10;
                }
                break;
            case 317:
                {
                alt169=11;
                }
                break;
            case 318:
                {
                alt169=12;
                }
                break;
            case 319:
                {
                alt169=13;
                }
                break;
            case 320:
                {
                alt169=14;
                }
                break;
            case 321:
                {
                alt169=15;
                }
                break;
            case 322:
                {
                alt169=16;
                }
                break;
            case 323:
                {
                alt169=17;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 169, 0, input);

                throw nvae;
            }

            switch (alt169) {
                case 1 :
                    // InternalMlopsDsl.g:9381:3: (enumLiteral_0= 'CSV' )
                    {
                    // InternalMlopsDsl.g:9381:3: (enumLiteral_0= 'CSV' )
                    // InternalMlopsDsl.g:9382:4: enumLiteral_0= 'CSV'
                    {
                    enumLiteral_0=(Token)match(input,307,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getCSVEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDataSourceAccess().getCSVEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9389:3: (enumLiteral_1= 'PARQUET' )
                    {
                    // InternalMlopsDsl.g:9389:3: (enumLiteral_1= 'PARQUET' )
                    // InternalMlopsDsl.g:9390:4: enumLiteral_1= 'PARQUET'
                    {
                    enumLiteral_1=(Token)match(input,308,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getPARQUETEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDataSourceAccess().getPARQUETEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9397:3: (enumLiteral_2= 'JSONL' )
                    {
                    // InternalMlopsDsl.g:9397:3: (enumLiteral_2= 'JSONL' )
                    // InternalMlopsDsl.g:9398:4: enumLiteral_2= 'JSONL'
                    {
                    enumLiteral_2=(Token)match(input,309,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getJSONLEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDataSourceAccess().getJSONLEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9405:3: (enumLiteral_3= 'IMAGE_FOLDER' )
                    {
                    // InternalMlopsDsl.g:9405:3: (enumLiteral_3= 'IMAGE_FOLDER' )
                    // InternalMlopsDsl.g:9406:4: enumLiteral_3= 'IMAGE_FOLDER'
                    {
                    enumLiteral_3=(Token)match(input,310,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getIMAGE_FOLDEREnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getDataSourceAccess().getIMAGE_FOLDEREnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9413:3: (enumLiteral_4= 'VIDEO_FOLDER' )
                    {
                    // InternalMlopsDsl.g:9413:3: (enumLiteral_4= 'VIDEO_FOLDER' )
                    // InternalMlopsDsl.g:9414:4: enumLiteral_4= 'VIDEO_FOLDER'
                    {
                    enumLiteral_4=(Token)match(input,311,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getVIDEO_FOLDEREnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getDataSourceAccess().getVIDEO_FOLDEREnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalMlopsDsl.g:9421:3: (enumLiteral_5= 'AUDIO_FOLDER' )
                    {
                    // InternalMlopsDsl.g:9421:3: (enumLiteral_5= 'AUDIO_FOLDER' )
                    // InternalMlopsDsl.g:9422:4: enumLiteral_5= 'AUDIO_FOLDER'
                    {
                    enumLiteral_5=(Token)match(input,312,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getAUDIO_FOLDEREnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getDataSourceAccess().getAUDIO_FOLDEREnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalMlopsDsl.g:9429:3: (enumLiteral_6= 'TEXT_CORPUS' )
                    {
                    // InternalMlopsDsl.g:9429:3: (enumLiteral_6= 'TEXT_CORPUS' )
                    // InternalMlopsDsl.g:9430:4: enumLiteral_6= 'TEXT_CORPUS'
                    {
                    enumLiteral_6=(Token)match(input,313,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getTEXT_CORPUSEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getDataSourceAccess().getTEXT_CORPUSEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalMlopsDsl.g:9437:3: (enumLiteral_7= 'HUGGINGFACE_DATASET' )
                    {
                    // InternalMlopsDsl.g:9437:3: (enumLiteral_7= 'HUGGINGFACE_DATASET' )
                    // InternalMlopsDsl.g:9438:4: enumLiteral_7= 'HUGGINGFACE_DATASET'
                    {
                    enumLiteral_7=(Token)match(input,314,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getHUGGINGFACE_DATASETEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getDataSourceAccess().getHUGGINGFACE_DATASETEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalMlopsDsl.g:9445:3: (enumLiteral_8= 'S3' )
                    {
                    // InternalMlopsDsl.g:9445:3: (enumLiteral_8= 'S3' )
                    // InternalMlopsDsl.g:9446:4: enumLiteral_8= 'S3'
                    {
                    enumLiteral_8=(Token)match(input,315,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getS3EnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getDataSourceAccess().getS3EnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalMlopsDsl.g:9453:3: (enumLiteral_9= 'GCS' )
                    {
                    // InternalMlopsDsl.g:9453:3: (enumLiteral_9= 'GCS' )
                    // InternalMlopsDsl.g:9454:4: enumLiteral_9= 'GCS'
                    {
                    enumLiteral_9=(Token)match(input,316,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getGCSEnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getDataSourceAccess().getGCSEnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalMlopsDsl.g:9461:3: (enumLiteral_10= 'AZURE_BLOB' )
                    {
                    // InternalMlopsDsl.g:9461:3: (enumLiteral_10= 'AZURE_BLOB' )
                    // InternalMlopsDsl.g:9462:4: enumLiteral_10= 'AZURE_BLOB'
                    {
                    enumLiteral_10=(Token)match(input,317,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getAZURE_BLOBEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getDataSourceAccess().getAZURE_BLOBEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;
                case 12 :
                    // InternalMlopsDsl.g:9469:3: (enumLiteral_11= 'BIGQUERY' )
                    {
                    // InternalMlopsDsl.g:9469:3: (enumLiteral_11= 'BIGQUERY' )
                    // InternalMlopsDsl.g:9470:4: enumLiteral_11= 'BIGQUERY'
                    {
                    enumLiteral_11=(Token)match(input,318,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getBIGQUERYEnumLiteralDeclaration_11().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_11, grammarAccess.getDataSourceAccess().getBIGQUERYEnumLiteralDeclaration_11());
                    			

                    }


                    }
                    break;
                case 13 :
                    // InternalMlopsDsl.g:9477:3: (enumLiteral_12= 'SQL' )
                    {
                    // InternalMlopsDsl.g:9477:3: (enumLiteral_12= 'SQL' )
                    // InternalMlopsDsl.g:9478:4: enumLiteral_12= 'SQL'
                    {
                    enumLiteral_12=(Token)match(input,319,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getSQLEnumLiteralDeclaration_12().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_12, grammarAccess.getDataSourceAccess().getSQLEnumLiteralDeclaration_12());
                    			

                    }


                    }
                    break;
                case 14 :
                    // InternalMlopsDsl.g:9485:3: (enumLiteral_13= 'NOSQL' )
                    {
                    // InternalMlopsDsl.g:9485:3: (enumLiteral_13= 'NOSQL' )
                    // InternalMlopsDsl.g:9486:4: enumLiteral_13= 'NOSQL'
                    {
                    enumLiteral_13=(Token)match(input,320,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getNOSQLEnumLiteralDeclaration_13().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_13, grammarAccess.getDataSourceAccess().getNOSQLEnumLiteralDeclaration_13());
                    			

                    }


                    }
                    break;
                case 15 :
                    // InternalMlopsDsl.g:9493:3: (enumLiteral_14= 'KAFKA' )
                    {
                    // InternalMlopsDsl.g:9493:3: (enumLiteral_14= 'KAFKA' )
                    // InternalMlopsDsl.g:9494:4: enumLiteral_14= 'KAFKA'
                    {
                    enumLiteral_14=(Token)match(input,321,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getKAFKAEnumLiteralDeclaration_14().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_14, grammarAccess.getDataSourceAccess().getKAFKAEnumLiteralDeclaration_14());
                    			

                    }


                    }
                    break;
                case 16 :
                    // InternalMlopsDsl.g:9501:3: (enumLiteral_15= 'REST' )
                    {
                    // InternalMlopsDsl.g:9501:3: (enumLiteral_15= 'REST' )
                    // InternalMlopsDsl.g:9502:4: enumLiteral_15= 'REST'
                    {
                    enumLiteral_15=(Token)match(input,322,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getRESTEnumLiteralDeclaration_15().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_15, grammarAccess.getDataSourceAccess().getRESTEnumLiteralDeclaration_15());
                    			

                    }


                    }
                    break;
                case 17 :
                    // InternalMlopsDsl.g:9509:3: (enumLiteral_16= 'GRAPH' )
                    {
                    // InternalMlopsDsl.g:9509:3: (enumLiteral_16= 'GRAPH' )
                    // InternalMlopsDsl.g:9510:4: enumLiteral_16= 'GRAPH'
                    {
                    enumLiteral_16=(Token)match(input,323,FOLLOW_2); 

                    				current = grammarAccess.getDataSourceAccess().getGRAPHEnumLiteralDeclaration_16().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_16, grammarAccess.getDataSourceAccess().getGRAPHEnumLiteralDeclaration_16());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDataSource"


    // $ANTLR start "ruleDataModality"
    // InternalMlopsDsl.g:9520:1: ruleDataModality returns [Enumerator current=null] : ( (enumLiteral_0= 'TABULAR' ) | (enumLiteral_1= 'IMAGE' ) | (enumLiteral_2= 'AUDIO' ) | (enumLiteral_3= 'VIDEO' ) | (enumLiteral_4= 'TEXT' ) | (enumLiteral_5= 'MULTIMODAL' ) ) ;
    public final Enumerator ruleDataModality() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9526:2: ( ( (enumLiteral_0= 'TABULAR' ) | (enumLiteral_1= 'IMAGE' ) | (enumLiteral_2= 'AUDIO' ) | (enumLiteral_3= 'VIDEO' ) | (enumLiteral_4= 'TEXT' ) | (enumLiteral_5= 'MULTIMODAL' ) ) )
            // InternalMlopsDsl.g:9527:2: ( (enumLiteral_0= 'TABULAR' ) | (enumLiteral_1= 'IMAGE' ) | (enumLiteral_2= 'AUDIO' ) | (enumLiteral_3= 'VIDEO' ) | (enumLiteral_4= 'TEXT' ) | (enumLiteral_5= 'MULTIMODAL' ) )
            {
            // InternalMlopsDsl.g:9527:2: ( (enumLiteral_0= 'TABULAR' ) | (enumLiteral_1= 'IMAGE' ) | (enumLiteral_2= 'AUDIO' ) | (enumLiteral_3= 'VIDEO' ) | (enumLiteral_4= 'TEXT' ) | (enumLiteral_5= 'MULTIMODAL' ) )
            int alt170=6;
            switch ( input.LA(1) ) {
            case 324:
                {
                alt170=1;
                }
                break;
            case 325:
                {
                alt170=2;
                }
                break;
            case 326:
                {
                alt170=3;
                }
                break;
            case 327:
                {
                alt170=4;
                }
                break;
            case 328:
                {
                alt170=5;
                }
                break;
            case 329:
                {
                alt170=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 170, 0, input);

                throw nvae;
            }

            switch (alt170) {
                case 1 :
                    // InternalMlopsDsl.g:9528:3: (enumLiteral_0= 'TABULAR' )
                    {
                    // InternalMlopsDsl.g:9528:3: (enumLiteral_0= 'TABULAR' )
                    // InternalMlopsDsl.g:9529:4: enumLiteral_0= 'TABULAR'
                    {
                    enumLiteral_0=(Token)match(input,324,FOLLOW_2); 

                    				current = grammarAccess.getDataModalityAccess().getTABULAREnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getDataModalityAccess().getTABULAREnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9536:3: (enumLiteral_1= 'IMAGE' )
                    {
                    // InternalMlopsDsl.g:9536:3: (enumLiteral_1= 'IMAGE' )
                    // InternalMlopsDsl.g:9537:4: enumLiteral_1= 'IMAGE'
                    {
                    enumLiteral_1=(Token)match(input,325,FOLLOW_2); 

                    				current = grammarAccess.getDataModalityAccess().getIMAGEEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getDataModalityAccess().getIMAGEEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9544:3: (enumLiteral_2= 'AUDIO' )
                    {
                    // InternalMlopsDsl.g:9544:3: (enumLiteral_2= 'AUDIO' )
                    // InternalMlopsDsl.g:9545:4: enumLiteral_2= 'AUDIO'
                    {
                    enumLiteral_2=(Token)match(input,326,FOLLOW_2); 

                    				current = grammarAccess.getDataModalityAccess().getAUDIOEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getDataModalityAccess().getAUDIOEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9552:3: (enumLiteral_3= 'VIDEO' )
                    {
                    // InternalMlopsDsl.g:9552:3: (enumLiteral_3= 'VIDEO' )
                    // InternalMlopsDsl.g:9553:4: enumLiteral_3= 'VIDEO'
                    {
                    enumLiteral_3=(Token)match(input,327,FOLLOW_2); 

                    				current = grammarAccess.getDataModalityAccess().getVIDEOEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getDataModalityAccess().getVIDEOEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9560:3: (enumLiteral_4= 'TEXT' )
                    {
                    // InternalMlopsDsl.g:9560:3: (enumLiteral_4= 'TEXT' )
                    // InternalMlopsDsl.g:9561:4: enumLiteral_4= 'TEXT'
                    {
                    enumLiteral_4=(Token)match(input,328,FOLLOW_2); 

                    				current = grammarAccess.getDataModalityAccess().getTEXTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getDataModalityAccess().getTEXTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalMlopsDsl.g:9568:3: (enumLiteral_5= 'MULTIMODAL' )
                    {
                    // InternalMlopsDsl.g:9568:3: (enumLiteral_5= 'MULTIMODAL' )
                    // InternalMlopsDsl.g:9569:4: enumLiteral_5= 'MULTIMODAL'
                    {
                    enumLiteral_5=(Token)match(input,329,FOLLOW_2); 

                    				current = grammarAccess.getDataModalityAccess().getMULTIMODALEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getDataModalityAccess().getMULTIMODALEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleDataModality"


    // $ANTLR start "ruleArtifactKind"
    // InternalMlopsDsl.g:9579:1: ruleArtifactKind returns [Enumerator current=null] : ( (enumLiteral_0= 'DATASET' ) | (enumLiteral_1= 'MODEL' ) | (enumLiteral_2= 'METRICS' ) | (enumLiteral_3= 'IMAGE' ) | (enumLiteral_4= 'WHEEL' ) | (enumLiteral_5= 'LOG' ) | (enumLiteral_6= 'OTHER' ) ) ;
    public final Enumerator ruleArtifactKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9585:2: ( ( (enumLiteral_0= 'DATASET' ) | (enumLiteral_1= 'MODEL' ) | (enumLiteral_2= 'METRICS' ) | (enumLiteral_3= 'IMAGE' ) | (enumLiteral_4= 'WHEEL' ) | (enumLiteral_5= 'LOG' ) | (enumLiteral_6= 'OTHER' ) ) )
            // InternalMlopsDsl.g:9586:2: ( (enumLiteral_0= 'DATASET' ) | (enumLiteral_1= 'MODEL' ) | (enumLiteral_2= 'METRICS' ) | (enumLiteral_3= 'IMAGE' ) | (enumLiteral_4= 'WHEEL' ) | (enumLiteral_5= 'LOG' ) | (enumLiteral_6= 'OTHER' ) )
            {
            // InternalMlopsDsl.g:9586:2: ( (enumLiteral_0= 'DATASET' ) | (enumLiteral_1= 'MODEL' ) | (enumLiteral_2= 'METRICS' ) | (enumLiteral_3= 'IMAGE' ) | (enumLiteral_4= 'WHEEL' ) | (enumLiteral_5= 'LOG' ) | (enumLiteral_6= 'OTHER' ) )
            int alt171=7;
            switch ( input.LA(1) ) {
            case 330:
                {
                alt171=1;
                }
                break;
            case 331:
                {
                alt171=2;
                }
                break;
            case 332:
                {
                alt171=3;
                }
                break;
            case 325:
                {
                alt171=4;
                }
                break;
            case 251:
                {
                alt171=5;
                }
                break;
            case 333:
                {
                alt171=6;
                }
                break;
            case 334:
                {
                alt171=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 171, 0, input);

                throw nvae;
            }

            switch (alt171) {
                case 1 :
                    // InternalMlopsDsl.g:9587:3: (enumLiteral_0= 'DATASET' )
                    {
                    // InternalMlopsDsl.g:9587:3: (enumLiteral_0= 'DATASET' )
                    // InternalMlopsDsl.g:9588:4: enumLiteral_0= 'DATASET'
                    {
                    enumLiteral_0=(Token)match(input,330,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getDATASETEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getArtifactKindAccess().getDATASETEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9595:3: (enumLiteral_1= 'MODEL' )
                    {
                    // InternalMlopsDsl.g:9595:3: (enumLiteral_1= 'MODEL' )
                    // InternalMlopsDsl.g:9596:4: enumLiteral_1= 'MODEL'
                    {
                    enumLiteral_1=(Token)match(input,331,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getMODELEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getArtifactKindAccess().getMODELEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9603:3: (enumLiteral_2= 'METRICS' )
                    {
                    // InternalMlopsDsl.g:9603:3: (enumLiteral_2= 'METRICS' )
                    // InternalMlopsDsl.g:9604:4: enumLiteral_2= 'METRICS'
                    {
                    enumLiteral_2=(Token)match(input,332,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getMETRICSEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getArtifactKindAccess().getMETRICSEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9611:3: (enumLiteral_3= 'IMAGE' )
                    {
                    // InternalMlopsDsl.g:9611:3: (enumLiteral_3= 'IMAGE' )
                    // InternalMlopsDsl.g:9612:4: enumLiteral_3= 'IMAGE'
                    {
                    enumLiteral_3=(Token)match(input,325,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getIMAGEEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getArtifactKindAccess().getIMAGEEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9619:3: (enumLiteral_4= 'WHEEL' )
                    {
                    // InternalMlopsDsl.g:9619:3: (enumLiteral_4= 'WHEEL' )
                    // InternalMlopsDsl.g:9620:4: enumLiteral_4= 'WHEEL'
                    {
                    enumLiteral_4=(Token)match(input,251,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getWHEELEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getArtifactKindAccess().getWHEELEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalMlopsDsl.g:9627:3: (enumLiteral_5= 'LOG' )
                    {
                    // InternalMlopsDsl.g:9627:3: (enumLiteral_5= 'LOG' )
                    // InternalMlopsDsl.g:9628:4: enumLiteral_5= 'LOG'
                    {
                    enumLiteral_5=(Token)match(input,333,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getLOGEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getArtifactKindAccess().getLOGEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalMlopsDsl.g:9635:3: (enumLiteral_6= 'OTHER' )
                    {
                    // InternalMlopsDsl.g:9635:3: (enumLiteral_6= 'OTHER' )
                    // InternalMlopsDsl.g:9636:4: enumLiteral_6= 'OTHER'
                    {
                    enumLiteral_6=(Token)match(input,334,FOLLOW_2); 

                    				current = grammarAccess.getArtifactKindAccess().getOTHEREnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getArtifactKindAccess().getOTHEREnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleArtifactKind"


    // $ANTLR start "rulePipelineStageKind"
    // InternalMlopsDsl.g:9646:1: rulePipelineStageKind returns [Enumerator current=null] : ( (enumLiteral_0= 'INGEST' ) | (enumLiteral_1= 'VALIDATE_DATA' ) | (enumLiteral_2= 'VERSION_DATA' ) | (enumLiteral_3= 'TRAIN' ) | (enumLiteral_4= 'TRACK_EXPERIMENT' ) | (enumLiteral_5= 'EVALUATE' ) | (enumLiteral_6= 'REGISTER_MODEL' ) | (enumLiteral_7= 'DECISION_GATE' ) | (enumLiteral_8= 'DEPLOY' ) | (enumLiteral_9= 'MONITOR' ) | (enumLiteral_10= 'RETRAIN' ) ) ;
    public final Enumerator rulePipelineStageKind() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;
        Token enumLiteral_5=null;
        Token enumLiteral_6=null;
        Token enumLiteral_7=null;
        Token enumLiteral_8=null;
        Token enumLiteral_9=null;
        Token enumLiteral_10=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9652:2: ( ( (enumLiteral_0= 'INGEST' ) | (enumLiteral_1= 'VALIDATE_DATA' ) | (enumLiteral_2= 'VERSION_DATA' ) | (enumLiteral_3= 'TRAIN' ) | (enumLiteral_4= 'TRACK_EXPERIMENT' ) | (enumLiteral_5= 'EVALUATE' ) | (enumLiteral_6= 'REGISTER_MODEL' ) | (enumLiteral_7= 'DECISION_GATE' ) | (enumLiteral_8= 'DEPLOY' ) | (enumLiteral_9= 'MONITOR' ) | (enumLiteral_10= 'RETRAIN' ) ) )
            // InternalMlopsDsl.g:9653:2: ( (enumLiteral_0= 'INGEST' ) | (enumLiteral_1= 'VALIDATE_DATA' ) | (enumLiteral_2= 'VERSION_DATA' ) | (enumLiteral_3= 'TRAIN' ) | (enumLiteral_4= 'TRACK_EXPERIMENT' ) | (enumLiteral_5= 'EVALUATE' ) | (enumLiteral_6= 'REGISTER_MODEL' ) | (enumLiteral_7= 'DECISION_GATE' ) | (enumLiteral_8= 'DEPLOY' ) | (enumLiteral_9= 'MONITOR' ) | (enumLiteral_10= 'RETRAIN' ) )
            {
            // InternalMlopsDsl.g:9653:2: ( (enumLiteral_0= 'INGEST' ) | (enumLiteral_1= 'VALIDATE_DATA' ) | (enumLiteral_2= 'VERSION_DATA' ) | (enumLiteral_3= 'TRAIN' ) | (enumLiteral_4= 'TRACK_EXPERIMENT' ) | (enumLiteral_5= 'EVALUATE' ) | (enumLiteral_6= 'REGISTER_MODEL' ) | (enumLiteral_7= 'DECISION_GATE' ) | (enumLiteral_8= 'DEPLOY' ) | (enumLiteral_9= 'MONITOR' ) | (enumLiteral_10= 'RETRAIN' ) )
            int alt172=11;
            switch ( input.LA(1) ) {
            case 335:
                {
                alt172=1;
                }
                break;
            case 336:
                {
                alt172=2;
                }
                break;
            case 337:
                {
                alt172=3;
                }
                break;
            case 257:
                {
                alt172=4;
                }
                break;
            case 338:
                {
                alt172=5;
                }
                break;
            case 339:
                {
                alt172=6;
                }
                break;
            case 340:
                {
                alt172=7;
                }
                break;
            case 341:
                {
                alt172=8;
                }
                break;
            case 266:
                {
                alt172=9;
                }
                break;
            case 262:
                {
                alt172=10;
                }
                break;
            case 267:
                {
                alt172=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 172, 0, input);

                throw nvae;
            }

            switch (alt172) {
                case 1 :
                    // InternalMlopsDsl.g:9654:3: (enumLiteral_0= 'INGEST' )
                    {
                    // InternalMlopsDsl.g:9654:3: (enumLiteral_0= 'INGEST' )
                    // InternalMlopsDsl.g:9655:4: enumLiteral_0= 'INGEST'
                    {
                    enumLiteral_0=(Token)match(input,335,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getINGESTEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getPipelineStageKindAccess().getINGESTEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9662:3: (enumLiteral_1= 'VALIDATE_DATA' )
                    {
                    // InternalMlopsDsl.g:9662:3: (enumLiteral_1= 'VALIDATE_DATA' )
                    // InternalMlopsDsl.g:9663:4: enumLiteral_1= 'VALIDATE_DATA'
                    {
                    enumLiteral_1=(Token)match(input,336,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getVALIDATE_DATAEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getPipelineStageKindAccess().getVALIDATE_DATAEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9670:3: (enumLiteral_2= 'VERSION_DATA' )
                    {
                    // InternalMlopsDsl.g:9670:3: (enumLiteral_2= 'VERSION_DATA' )
                    // InternalMlopsDsl.g:9671:4: enumLiteral_2= 'VERSION_DATA'
                    {
                    enumLiteral_2=(Token)match(input,337,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getVERSION_DATAEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getPipelineStageKindAccess().getVERSION_DATAEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9678:3: (enumLiteral_3= 'TRAIN' )
                    {
                    // InternalMlopsDsl.g:9678:3: (enumLiteral_3= 'TRAIN' )
                    // InternalMlopsDsl.g:9679:4: enumLiteral_3= 'TRAIN'
                    {
                    enumLiteral_3=(Token)match(input,257,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getTRAINEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getPipelineStageKindAccess().getTRAINEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9686:3: (enumLiteral_4= 'TRACK_EXPERIMENT' )
                    {
                    // InternalMlopsDsl.g:9686:3: (enumLiteral_4= 'TRACK_EXPERIMENT' )
                    // InternalMlopsDsl.g:9687:4: enumLiteral_4= 'TRACK_EXPERIMENT'
                    {
                    enumLiteral_4=(Token)match(input,338,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getTRACK_EXPERIMENTEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getPipelineStageKindAccess().getTRACK_EXPERIMENTEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;
                case 6 :
                    // InternalMlopsDsl.g:9694:3: (enumLiteral_5= 'EVALUATE' )
                    {
                    // InternalMlopsDsl.g:9694:3: (enumLiteral_5= 'EVALUATE' )
                    // InternalMlopsDsl.g:9695:4: enumLiteral_5= 'EVALUATE'
                    {
                    enumLiteral_5=(Token)match(input,339,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getEVALUATEEnumLiteralDeclaration_5().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_5, grammarAccess.getPipelineStageKindAccess().getEVALUATEEnumLiteralDeclaration_5());
                    			

                    }


                    }
                    break;
                case 7 :
                    // InternalMlopsDsl.g:9702:3: (enumLiteral_6= 'REGISTER_MODEL' )
                    {
                    // InternalMlopsDsl.g:9702:3: (enumLiteral_6= 'REGISTER_MODEL' )
                    // InternalMlopsDsl.g:9703:4: enumLiteral_6= 'REGISTER_MODEL'
                    {
                    enumLiteral_6=(Token)match(input,340,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getREGISTER_MODELEnumLiteralDeclaration_6().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_6, grammarAccess.getPipelineStageKindAccess().getREGISTER_MODELEnumLiteralDeclaration_6());
                    			

                    }


                    }
                    break;
                case 8 :
                    // InternalMlopsDsl.g:9710:3: (enumLiteral_7= 'DECISION_GATE' )
                    {
                    // InternalMlopsDsl.g:9710:3: (enumLiteral_7= 'DECISION_GATE' )
                    // InternalMlopsDsl.g:9711:4: enumLiteral_7= 'DECISION_GATE'
                    {
                    enumLiteral_7=(Token)match(input,341,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getDECISION_GATEEnumLiteralDeclaration_7().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_7, grammarAccess.getPipelineStageKindAccess().getDECISION_GATEEnumLiteralDeclaration_7());
                    			

                    }


                    }
                    break;
                case 9 :
                    // InternalMlopsDsl.g:9718:3: (enumLiteral_8= 'DEPLOY' )
                    {
                    // InternalMlopsDsl.g:9718:3: (enumLiteral_8= 'DEPLOY' )
                    // InternalMlopsDsl.g:9719:4: enumLiteral_8= 'DEPLOY'
                    {
                    enumLiteral_8=(Token)match(input,266,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getDEPLOYEnumLiteralDeclaration_8().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_8, grammarAccess.getPipelineStageKindAccess().getDEPLOYEnumLiteralDeclaration_8());
                    			

                    }


                    }
                    break;
                case 10 :
                    // InternalMlopsDsl.g:9726:3: (enumLiteral_9= 'MONITOR' )
                    {
                    // InternalMlopsDsl.g:9726:3: (enumLiteral_9= 'MONITOR' )
                    // InternalMlopsDsl.g:9727:4: enumLiteral_9= 'MONITOR'
                    {
                    enumLiteral_9=(Token)match(input,262,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getMONITOREnumLiteralDeclaration_9().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_9, grammarAccess.getPipelineStageKindAccess().getMONITOREnumLiteralDeclaration_9());
                    			

                    }


                    }
                    break;
                case 11 :
                    // InternalMlopsDsl.g:9734:3: (enumLiteral_10= 'RETRAIN' )
                    {
                    // InternalMlopsDsl.g:9734:3: (enumLiteral_10= 'RETRAIN' )
                    // InternalMlopsDsl.g:9735:4: enumLiteral_10= 'RETRAIN'
                    {
                    enumLiteral_10=(Token)match(input,267,FOLLOW_2); 

                    				current = grammarAccess.getPipelineStageKindAccess().getRETRAINEnumLiteralDeclaration_10().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_10, grammarAccess.getPipelineStageKindAccess().getRETRAINEnumLiteralDeclaration_10());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "rulePipelineStageKind"


    // $ANTLR start "ruleRetrainingStrategy"
    // InternalMlopsDsl.g:9745:1: ruleRetrainingStrategy returns [Enumerator current=null] : ( (enumLiteral_0= 'MANUAL' ) | (enumLiteral_1= 'SCHEDULED' ) | (enumLiteral_2= 'DRIFT_BASED' ) | (enumLiteral_3= 'PERFORMANCE_BASED' ) | (enumLiteral_4= 'HYBRID' ) ) ;
    public final Enumerator ruleRetrainingStrategy() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9751:2: ( ( (enumLiteral_0= 'MANUAL' ) | (enumLiteral_1= 'SCHEDULED' ) | (enumLiteral_2= 'DRIFT_BASED' ) | (enumLiteral_3= 'PERFORMANCE_BASED' ) | (enumLiteral_4= 'HYBRID' ) ) )
            // InternalMlopsDsl.g:9752:2: ( (enumLiteral_0= 'MANUAL' ) | (enumLiteral_1= 'SCHEDULED' ) | (enumLiteral_2= 'DRIFT_BASED' ) | (enumLiteral_3= 'PERFORMANCE_BASED' ) | (enumLiteral_4= 'HYBRID' ) )
            {
            // InternalMlopsDsl.g:9752:2: ( (enumLiteral_0= 'MANUAL' ) | (enumLiteral_1= 'SCHEDULED' ) | (enumLiteral_2= 'DRIFT_BASED' ) | (enumLiteral_3= 'PERFORMANCE_BASED' ) | (enumLiteral_4= 'HYBRID' ) )
            int alt173=5;
            switch ( input.LA(1) ) {
            case 342:
                {
                alt173=1;
                }
                break;
            case 343:
                {
                alt173=2;
                }
                break;
            case 344:
                {
                alt173=3;
                }
                break;
            case 345:
                {
                alt173=4;
                }
                break;
            case 346:
                {
                alt173=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 173, 0, input);

                throw nvae;
            }

            switch (alt173) {
                case 1 :
                    // InternalMlopsDsl.g:9753:3: (enumLiteral_0= 'MANUAL' )
                    {
                    // InternalMlopsDsl.g:9753:3: (enumLiteral_0= 'MANUAL' )
                    // InternalMlopsDsl.g:9754:4: enumLiteral_0= 'MANUAL'
                    {
                    enumLiteral_0=(Token)match(input,342,FOLLOW_2); 

                    				current = grammarAccess.getRetrainingStrategyAccess().getMANUALEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getRetrainingStrategyAccess().getMANUALEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9761:3: (enumLiteral_1= 'SCHEDULED' )
                    {
                    // InternalMlopsDsl.g:9761:3: (enumLiteral_1= 'SCHEDULED' )
                    // InternalMlopsDsl.g:9762:4: enumLiteral_1= 'SCHEDULED'
                    {
                    enumLiteral_1=(Token)match(input,343,FOLLOW_2); 

                    				current = grammarAccess.getRetrainingStrategyAccess().getSCHEDULEDEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getRetrainingStrategyAccess().getSCHEDULEDEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9769:3: (enumLiteral_2= 'DRIFT_BASED' )
                    {
                    // InternalMlopsDsl.g:9769:3: (enumLiteral_2= 'DRIFT_BASED' )
                    // InternalMlopsDsl.g:9770:4: enumLiteral_2= 'DRIFT_BASED'
                    {
                    enumLiteral_2=(Token)match(input,344,FOLLOW_2); 

                    				current = grammarAccess.getRetrainingStrategyAccess().getDRIFT_BASEDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getRetrainingStrategyAccess().getDRIFT_BASEDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9777:3: (enumLiteral_3= 'PERFORMANCE_BASED' )
                    {
                    // InternalMlopsDsl.g:9777:3: (enumLiteral_3= 'PERFORMANCE_BASED' )
                    // InternalMlopsDsl.g:9778:4: enumLiteral_3= 'PERFORMANCE_BASED'
                    {
                    enumLiteral_3=(Token)match(input,345,FOLLOW_2); 

                    				current = grammarAccess.getRetrainingStrategyAccess().getPERFORMANCE_BASEDEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getRetrainingStrategyAccess().getPERFORMANCE_BASEDEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9785:3: (enumLiteral_4= 'HYBRID' )
                    {
                    // InternalMlopsDsl.g:9785:3: (enumLiteral_4= 'HYBRID' )
                    // InternalMlopsDsl.g:9786:4: enumLiteral_4= 'HYBRID'
                    {
                    enumLiteral_4=(Token)match(input,346,FOLLOW_2); 

                    				current = grammarAccess.getRetrainingStrategyAccess().getHYBRIDEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getRetrainingStrategyAccess().getHYBRIDEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRetrainingStrategy"


    // $ANTLR start "ruleTrackingBackend"
    // InternalMlopsDsl.g:9796:1: ruleTrackingBackend returns [Enumerator current=null] : ( (enumLiteral_0= 'MLFLOW' ) | (enumLiteral_1= 'WANDB' ) | (enumLiteral_2= 'TENSORBOARD' ) | (enumLiteral_3= 'CUSTOM' ) ) ;
    public final Enumerator ruleTrackingBackend() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9802:2: ( ( (enumLiteral_0= 'MLFLOW' ) | (enumLiteral_1= 'WANDB' ) | (enumLiteral_2= 'TENSORBOARD' ) | (enumLiteral_3= 'CUSTOM' ) ) )
            // InternalMlopsDsl.g:9803:2: ( (enumLiteral_0= 'MLFLOW' ) | (enumLiteral_1= 'WANDB' ) | (enumLiteral_2= 'TENSORBOARD' ) | (enumLiteral_3= 'CUSTOM' ) )
            {
            // InternalMlopsDsl.g:9803:2: ( (enumLiteral_0= 'MLFLOW' ) | (enumLiteral_1= 'WANDB' ) | (enumLiteral_2= 'TENSORBOARD' ) | (enumLiteral_3= 'CUSTOM' ) )
            int alt174=4;
            switch ( input.LA(1) ) {
            case 347:
                {
                alt174=1;
                }
                break;
            case 348:
                {
                alt174=2;
                }
                break;
            case 349:
                {
                alt174=3;
                }
                break;
            case 350:
                {
                alt174=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }

            switch (alt174) {
                case 1 :
                    // InternalMlopsDsl.g:9804:3: (enumLiteral_0= 'MLFLOW' )
                    {
                    // InternalMlopsDsl.g:9804:3: (enumLiteral_0= 'MLFLOW' )
                    // InternalMlopsDsl.g:9805:4: enumLiteral_0= 'MLFLOW'
                    {
                    enumLiteral_0=(Token)match(input,347,FOLLOW_2); 

                    				current = grammarAccess.getTrackingBackendAccess().getMLFLOWEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getTrackingBackendAccess().getMLFLOWEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9812:3: (enumLiteral_1= 'WANDB' )
                    {
                    // InternalMlopsDsl.g:9812:3: (enumLiteral_1= 'WANDB' )
                    // InternalMlopsDsl.g:9813:4: enumLiteral_1= 'WANDB'
                    {
                    enumLiteral_1=(Token)match(input,348,FOLLOW_2); 

                    				current = grammarAccess.getTrackingBackendAccess().getWANDBEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getTrackingBackendAccess().getWANDBEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9820:3: (enumLiteral_2= 'TENSORBOARD' )
                    {
                    // InternalMlopsDsl.g:9820:3: (enumLiteral_2= 'TENSORBOARD' )
                    // InternalMlopsDsl.g:9821:4: enumLiteral_2= 'TENSORBOARD'
                    {
                    enumLiteral_2=(Token)match(input,349,FOLLOW_2); 

                    				current = grammarAccess.getTrackingBackendAccess().getTENSORBOARDEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getTrackingBackendAccess().getTENSORBOARDEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9828:3: (enumLiteral_3= 'CUSTOM' )
                    {
                    // InternalMlopsDsl.g:9828:3: (enumLiteral_3= 'CUSTOM' )
                    // InternalMlopsDsl.g:9829:4: enumLiteral_3= 'CUSTOM'
                    {
                    enumLiteral_3=(Token)match(input,350,FOLLOW_2); 

                    				current = grammarAccess.getTrackingBackendAccess().getCUSTOMEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getTrackingBackendAccess().getCUSTOMEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleTrackingBackend"


    // $ANTLR start "ruleVersioningBackend"
    // InternalMlopsDsl.g:9839:1: ruleVersioningBackend returns [Enumerator current=null] : ( (enumLiteral_0= 'DVC' ) | (enumLiteral_1= 'GIT_LFS' ) | (enumLiteral_2= 'LAKEFS' ) | (enumLiteral_3= 'CUSTOM' ) ) ;
    public final Enumerator ruleVersioningBackend() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9845:2: ( ( (enumLiteral_0= 'DVC' ) | (enumLiteral_1= 'GIT_LFS' ) | (enumLiteral_2= 'LAKEFS' ) | (enumLiteral_3= 'CUSTOM' ) ) )
            // InternalMlopsDsl.g:9846:2: ( (enumLiteral_0= 'DVC' ) | (enumLiteral_1= 'GIT_LFS' ) | (enumLiteral_2= 'LAKEFS' ) | (enumLiteral_3= 'CUSTOM' ) )
            {
            // InternalMlopsDsl.g:9846:2: ( (enumLiteral_0= 'DVC' ) | (enumLiteral_1= 'GIT_LFS' ) | (enumLiteral_2= 'LAKEFS' ) | (enumLiteral_3= 'CUSTOM' ) )
            int alt175=4;
            switch ( input.LA(1) ) {
            case 351:
                {
                alt175=1;
                }
                break;
            case 352:
                {
                alt175=2;
                }
                break;
            case 353:
                {
                alt175=3;
                }
                break;
            case 350:
                {
                alt175=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                throw nvae;
            }

            switch (alt175) {
                case 1 :
                    // InternalMlopsDsl.g:9847:3: (enumLiteral_0= 'DVC' )
                    {
                    // InternalMlopsDsl.g:9847:3: (enumLiteral_0= 'DVC' )
                    // InternalMlopsDsl.g:9848:4: enumLiteral_0= 'DVC'
                    {
                    enumLiteral_0=(Token)match(input,351,FOLLOW_2); 

                    				current = grammarAccess.getVersioningBackendAccess().getDVCEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getVersioningBackendAccess().getDVCEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9855:3: (enumLiteral_1= 'GIT_LFS' )
                    {
                    // InternalMlopsDsl.g:9855:3: (enumLiteral_1= 'GIT_LFS' )
                    // InternalMlopsDsl.g:9856:4: enumLiteral_1= 'GIT_LFS'
                    {
                    enumLiteral_1=(Token)match(input,352,FOLLOW_2); 

                    				current = grammarAccess.getVersioningBackendAccess().getGIT_LFSEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getVersioningBackendAccess().getGIT_LFSEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9863:3: (enumLiteral_2= 'LAKEFS' )
                    {
                    // InternalMlopsDsl.g:9863:3: (enumLiteral_2= 'LAKEFS' )
                    // InternalMlopsDsl.g:9864:4: enumLiteral_2= 'LAKEFS'
                    {
                    enumLiteral_2=(Token)match(input,353,FOLLOW_2); 

                    				current = grammarAccess.getVersioningBackendAccess().getLAKEFSEnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getVersioningBackendAccess().getLAKEFSEnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9871:3: (enumLiteral_3= 'CUSTOM' )
                    {
                    // InternalMlopsDsl.g:9871:3: (enumLiteral_3= 'CUSTOM' )
                    // InternalMlopsDsl.g:9872:4: enumLiteral_3= 'CUSTOM'
                    {
                    enumLiteral_3=(Token)match(input,350,FOLLOW_2); 

                    				current = grammarAccess.getVersioningBackendAccess().getCUSTOMEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getVersioningBackendAccess().getCUSTOMEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleVersioningBackend"


    // $ANTLR start "ruleRegistryBackend"
    // InternalMlopsDsl.g:9882:1: ruleRegistryBackend returns [Enumerator current=null] : ( (enumLiteral_0= 'MLFLOW_REGISTRY' ) | (enumLiteral_1= 'AZURE_ML' ) | (enumLiteral_2= 'SAGEMAKER' ) | (enumLiteral_3= 'VERTEX_AI' ) | (enumLiteral_4= 'CUSTOM' ) ) ;
    public final Enumerator ruleRegistryBackend() throws RecognitionException {
        Enumerator current = null;

        Token enumLiteral_0=null;
        Token enumLiteral_1=null;
        Token enumLiteral_2=null;
        Token enumLiteral_3=null;
        Token enumLiteral_4=null;


        	enterRule();

        try {
            // InternalMlopsDsl.g:9888:2: ( ( (enumLiteral_0= 'MLFLOW_REGISTRY' ) | (enumLiteral_1= 'AZURE_ML' ) | (enumLiteral_2= 'SAGEMAKER' ) | (enumLiteral_3= 'VERTEX_AI' ) | (enumLiteral_4= 'CUSTOM' ) ) )
            // InternalMlopsDsl.g:9889:2: ( (enumLiteral_0= 'MLFLOW_REGISTRY' ) | (enumLiteral_1= 'AZURE_ML' ) | (enumLiteral_2= 'SAGEMAKER' ) | (enumLiteral_3= 'VERTEX_AI' ) | (enumLiteral_4= 'CUSTOM' ) )
            {
            // InternalMlopsDsl.g:9889:2: ( (enumLiteral_0= 'MLFLOW_REGISTRY' ) | (enumLiteral_1= 'AZURE_ML' ) | (enumLiteral_2= 'SAGEMAKER' ) | (enumLiteral_3= 'VERTEX_AI' ) | (enumLiteral_4= 'CUSTOM' ) )
            int alt176=5;
            switch ( input.LA(1) ) {
            case 354:
                {
                alt176=1;
                }
                break;
            case 355:
                {
                alt176=2;
                }
                break;
            case 356:
                {
                alt176=3;
                }
                break;
            case 357:
                {
                alt176=4;
                }
                break;
            case 350:
                {
                alt176=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 176, 0, input);

                throw nvae;
            }

            switch (alt176) {
                case 1 :
                    // InternalMlopsDsl.g:9890:3: (enumLiteral_0= 'MLFLOW_REGISTRY' )
                    {
                    // InternalMlopsDsl.g:9890:3: (enumLiteral_0= 'MLFLOW_REGISTRY' )
                    // InternalMlopsDsl.g:9891:4: enumLiteral_0= 'MLFLOW_REGISTRY'
                    {
                    enumLiteral_0=(Token)match(input,354,FOLLOW_2); 

                    				current = grammarAccess.getRegistryBackendAccess().getMLFLOW_REGISTRYEnumLiteralDeclaration_0().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_0, grammarAccess.getRegistryBackendAccess().getMLFLOW_REGISTRYEnumLiteralDeclaration_0());
                    			

                    }


                    }
                    break;
                case 2 :
                    // InternalMlopsDsl.g:9898:3: (enumLiteral_1= 'AZURE_ML' )
                    {
                    // InternalMlopsDsl.g:9898:3: (enumLiteral_1= 'AZURE_ML' )
                    // InternalMlopsDsl.g:9899:4: enumLiteral_1= 'AZURE_ML'
                    {
                    enumLiteral_1=(Token)match(input,355,FOLLOW_2); 

                    				current = grammarAccess.getRegistryBackendAccess().getAZURE_MLEnumLiteralDeclaration_1().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_1, grammarAccess.getRegistryBackendAccess().getAZURE_MLEnumLiteralDeclaration_1());
                    			

                    }


                    }
                    break;
                case 3 :
                    // InternalMlopsDsl.g:9906:3: (enumLiteral_2= 'SAGEMAKER' )
                    {
                    // InternalMlopsDsl.g:9906:3: (enumLiteral_2= 'SAGEMAKER' )
                    // InternalMlopsDsl.g:9907:4: enumLiteral_2= 'SAGEMAKER'
                    {
                    enumLiteral_2=(Token)match(input,356,FOLLOW_2); 

                    				current = grammarAccess.getRegistryBackendAccess().getSAGEMAKEREnumLiteralDeclaration_2().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_2, grammarAccess.getRegistryBackendAccess().getSAGEMAKEREnumLiteralDeclaration_2());
                    			

                    }


                    }
                    break;
                case 4 :
                    // InternalMlopsDsl.g:9914:3: (enumLiteral_3= 'VERTEX_AI' )
                    {
                    // InternalMlopsDsl.g:9914:3: (enumLiteral_3= 'VERTEX_AI' )
                    // InternalMlopsDsl.g:9915:4: enumLiteral_3= 'VERTEX_AI'
                    {
                    enumLiteral_3=(Token)match(input,357,FOLLOW_2); 

                    				current = grammarAccess.getRegistryBackendAccess().getVERTEX_AIEnumLiteralDeclaration_3().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_3, grammarAccess.getRegistryBackendAccess().getVERTEX_AIEnumLiteralDeclaration_3());
                    			

                    }


                    }
                    break;
                case 5 :
                    // InternalMlopsDsl.g:9922:3: (enumLiteral_4= 'CUSTOM' )
                    {
                    // InternalMlopsDsl.g:9922:3: (enumLiteral_4= 'CUSTOM' )
                    // InternalMlopsDsl.g:9923:4: enumLiteral_4= 'CUSTOM'
                    {
                    enumLiteral_4=(Token)match(input,350,FOLLOW_2); 

                    				current = grammarAccess.getRegistryBackendAccess().getCUSTOMEnumLiteralDeclaration_4().getEnumLiteral().getInstance();
                    				newLeafNode(enumLiteral_4, grammarAccess.getRegistryBackendAccess().getCUSTOMEnumLiteralDeclaration_4());
                    			

                    }


                    }
                    break;

            }


            }


            	leaveRule();

        }

            catch (RecognitionException re) {
                recover(input,re);
                appendSkippedTokens();
            }
        finally {
        }
        return current;
    }
    // $ANTLR end "ruleRegistryBackend"

    // Delegated rules


 

    public static final BitSet FOLLOW_1 = new BitSet(new long[]{0x0000000000000000L});
    public static final BitSet FOLLOW_2 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_3 = new BitSet(new long[]{0x4000804201021002L,0x1010801090030000L,0x0008804020402004L,0x0004000000400000L});
    public static final BitSet FOLLOW_4 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_5 = new BitSet(new long[]{0x0000000000004000L});
    public static final BitSet FOLLOW_6 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_7 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_8 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_9 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_10 = new BitSet(new long[]{0x0000000000180000L});
    public static final BitSet FOLLOW_11 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_12 = new BitSet(new long[]{0x0000000000E10000L});
    public static final BitSet FOLLOW_13 = new BitSet(new long[]{0x0000000000C10000L});
    public static final BitSet FOLLOW_14 = new BitSet(new long[]{0x0000000000810000L});
    public static final BitSet FOLLOW_15 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_16 = new BitSet(new long[]{0x0000000004000000L});
    public static final BitSet FOLLOW_17 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_18 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_19 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_20 = new BitSet(new long[]{0x0000000060010000L});
    public static final BitSet FOLLOW_21 = new BitSet(new long[]{0x0000000180000000L});
    public static final BitSet FOLLOW_22 = new BitSet(new long[]{0x0000000040010000L});
    public static final BitSet FOLLOW_23 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_24 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_25 = new BitSet(new long[]{0x4000807201021000L,0x1010801090030000L,0x0008804020402004L,0x0004000000400000L});
    public static final BitSet FOLLOW_26 = new BitSet(new long[]{0x0000003000000000L});
    public static final BitSet FOLLOW_27 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_28 = new BitSet(new long[]{0x0000030000000000L});
    public static final BitSet FOLLOW_29 = new BitSet(new long[]{0x0000020000000000L});
    public static final BitSet FOLLOW_30 = new BitSet(new long[]{0x0000040000000000L});
    public static final BitSet FOLLOW_31 = new BitSet(new long[]{0x0000080000000000L});
    public static final BitSet FOLLOW_32 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_33 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_34 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_35 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_36 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_37 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_38 = new BitSet(new long[]{0x0002000000000000L});
    public static final BitSet FOLLOW_39 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_40 = new BitSet(new long[]{0x0008000000000000L});
    public static final BitSet FOLLOW_41 = new BitSet(new long[]{0x0010000000000000L});
    public static final BitSet FOLLOW_42 = new BitSet(new long[]{0x0020000000000000L});
    public static final BitSet FOLLOW_43 = new BitSet(new long[]{0x0040000000000000L});
    public static final BitSet FOLLOW_44 = new BitSet(new long[]{0x0080000000000000L});
    public static final BitSet FOLLOW_45 = new BitSet(new long[]{0x0100000000000000L});
    public static final BitSet FOLLOW_46 = new BitSet(new long[]{0x0200000000000000L});
    public static final BitSet FOLLOW_47 = new BitSet(new long[]{0x0400000000000000L});
    public static final BitSet FOLLOW_48 = new BitSet(new long[]{0x0800000000000000L});
    public static final BitSet FOLLOW_49 = new BitSet(new long[]{0x1000000000000000L});
    public static final BitSet FOLLOW_50 = new BitSet(new long[]{0x2000000000000000L});
    public static final BitSet FOLLOW_51 = new BitSet(new long[]{0x8000000000000000L});
    public static final BitSet FOLLOW_52 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0xFFF8000000000000L,0x000000000000000FL});
    public static final BitSet FOLLOW_53 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_54 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000003F0L});
    public static final BitSet FOLLOW_55 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_56 = new BitSet(new long[]{0x0000008000010000L,0x00000000000007FCL});
    public static final BitSet FOLLOW_57 = new BitSet(new long[]{0x0000008000010000L,0x00000000000007F8L});
    public static final BitSet FOLLOW_58 = new BitSet(new long[]{0x0000008000010000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_59 = new BitSet(new long[]{0x0000000000010000L,0x00000000000007F0L});
    public static final BitSet FOLLOW_60 = new BitSet(new long[]{0x0000000000000020L,0x0000000000000000L,0x0000000000000000L,0x0060000000000000L});
    public static final BitSet FOLLOW_61 = new BitSet(new long[]{0x0000000000010000L,0x00000000000007E0L});
    public static final BitSet FOLLOW_62 = new BitSet(new long[]{0x0000000000010000L,0x00000000000007C0L});
    public static final BitSet FOLLOW_63 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000780L});
    public static final BitSet FOLLOW_64 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000700L});
    public static final BitSet FOLLOW_65 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000600L});
    public static final BitSet FOLLOW_66 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_67 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000400L});
    public static final BitSet FOLLOW_68 = new BitSet(new long[]{0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_69 = new BitSet(new long[]{0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_70 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_71 = new BitSet(new long[]{0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_72 = new BitSet(new long[]{0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_73 = new BitSet(new long[]{0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_74 = new BitSet(new long[]{0x0000000000010000L,0x0000000001F80000L});
    public static final BitSet FOLLOW_75 = new BitSet(new long[]{0x0000000000010000L,0x0000000001F00000L});
    public static final BitSet FOLLOW_76 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_77 = new BitSet(new long[]{0x0000000000010000L,0x0000000001E00000L});
    public static final BitSet FOLLOW_78 = new BitSet(new long[]{0x0000000000010000L,0x0000000001C00000L});
    public static final BitSet FOLLOW_79 = new BitSet(new long[]{0x0000000000010000L,0x0000000001800000L});
    public static final BitSet FOLLOW_80 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_81 = new BitSet(new long[]{0x0000000000010000L,0x0000000001000000L});
    public static final BitSet FOLLOW_82 = new BitSet(new long[]{0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_83 = new BitSet(new long[]{0x0000000000010000L,0x0000000008000000L});
    public static final BitSet FOLLOW_84 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000F80000L});
    public static final BitSet FOLLOW_85 = new BitSet(new long[]{0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_86 = new BitSet(new long[]{0x0000000000010000L,0x0000000040000000L});
    public static final BitSet FOLLOW_87 = new BitSet(new long[]{0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_88 = new BitSet(new long[]{0x0000000000010000L,0x0000000F00000000L});
    public static final BitSet FOLLOW_89 = new BitSet(new long[]{0x0000000000010000L,0x0000000E00000000L});
    public static final BitSet FOLLOW_90 = new BitSet(new long[]{0x0000000000010000L,0x0000000C00000000L});
    public static final BitSet FOLLOW_91 = new BitSet(new long[]{0x0000000000010000L,0x0000000800000000L});
    public static final BitSet FOLLOW_92 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000007000000L});
    public static final BitSet FOLLOW_93 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0xE400000000000000L});
    public static final BitSet FOLLOW_94 = new BitSet(new long[]{0x0000000000010000L,0x00000FE001800000L});
    public static final BitSet FOLLOW_95 = new BitSet(new long[]{0x0000000000010000L,0x00000FC001800000L});
    public static final BitSet FOLLOW_96 = new BitSet(new long[]{0x0000000000010000L,0x00000F8001800000L});
    public static final BitSet FOLLOW_97 = new BitSet(new long[]{0x0000000000010000L,0x00000F0001800000L});
    public static final BitSet FOLLOW_98 = new BitSet(new long[]{0x0000000000010000L,0x00000E0001800000L});
    public static final BitSet FOLLOW_99 = new BitSet(new long[]{0x0000000000010000L,0x00000C0001800000L});
    public static final BitSet FOLLOW_100 = new BitSet(new long[]{0x0000000000010000L,0x0000080001800000L});
    public static final BitSet FOLLOW_101 = new BitSet(new long[]{0x0000000000010000L,0x0000080001000000L});
    public static final BitSet FOLLOW_102 = new BitSet(new long[]{0x0000000000010000L,0x0000080000000000L});
    public static final BitSet FOLLOW_103 = new BitSet(new long[]{0x0000000000010000L,0x0000600000000000L});
    public static final BitSet FOLLOW_104 = new BitSet(new long[]{0x0000000000010000L,0x0000400000000000L});
    public static final BitSet FOLLOW_105 = new BitSet(new long[]{0x0000000000010000L,0x000F000000000000L});
    public static final BitSet FOLLOW_106 = new BitSet(new long[]{0x0000000000010000L,0x000E000000000000L});
    public static final BitSet FOLLOW_107 = new BitSet(new long[]{0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_108 = new BitSet(new long[]{0x0000000000010000L,0x000C000000000000L});
    public static final BitSet FOLLOW_109 = new BitSet(new long[]{0x0000000000010000L,0x0008000000000000L});
    public static final BitSet FOLLOW_110 = new BitSet(new long[]{0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000078000000L});
    public static final BitSet FOLLOW_112 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_113 = new BitSet(new long[]{0x0000000000010000L,0x0F80000000000000L});
    public static final BitSet FOLLOW_114 = new BitSet(new long[]{0x0000000000010000L,0x0F00000000000000L});
    public static final BitSet FOLLOW_115 = new BitSet(new long[]{0x0000000000010000L,0x0E00000000000000L});
    public static final BitSet FOLLOW_116 = new BitSet(new long[]{0x0000000000010000L,0x0C00000000000000L});
    public static final BitSet FOLLOW_117 = new BitSet(new long[]{0x0000000000010000L,0x0800000000000000L});
    public static final BitSet FOLLOW_118 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000003C0000000L});
    public static final BitSet FOLLOW_119 = new BitSet(new long[]{0x0000000000010000L,0xE000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_120 = new BitSet(new long[]{0x0000000000010000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_121 = new BitSet(new long[]{0x0000000000010000L,0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_122 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_123 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_124 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000003C40000000L});
    public static final BitSet FOLLOW_125 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_126 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x00000000000000F0L});
    public static final BitSet FOLLOW_127 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x00000000000000E0L});
    public static final BitSet FOLLOW_128 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_129 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_130 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_131 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000001C00L});
    public static final BitSet FOLLOW_132 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_133 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_134 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000003FC000L});
    public static final BitSet FOLLOW_135 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_136 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000003F0000L});
    public static final BitSet FOLLOW_137 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000003E0000L});
    public static final BitSet FOLLOW_138 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00000000003C0000L});
    public static final BitSet FOLLOW_139 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000380000L});
    public static final BitSet FOLLOW_140 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000300000L});
    public static final BitSet FOLLOW_141 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_143 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_144 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000007C00000L});
    public static final BitSet FOLLOW_145 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_146 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x000000001E000000L});
    public static final BitSet FOLLOW_147 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x000000001C000000L});
    public static final BitSet FOLLOW_148 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000018000000L});
    public static final BitSet FOLLOW_149 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_150 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x00000007C0000000L});
    public static final BitSet FOLLOW_151 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000780000000L});
    public static final BitSet FOLLOW_152 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000700000000L});
    public static final BitSet FOLLOW_153 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000600000000L});
    public static final BitSet FOLLOW_154 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_155 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000C42L,0x00000000003F8000L});
    public static final BitSet FOLLOW_156 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000003000001000L});
    public static final BitSet FOLLOW_157 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_158 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_159 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_160 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000078000L});
    public static final BitSet FOLLOW_161 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_162 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00001E0000000000L});
    public static final BitSet FOLLOW_163 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x00001C0000000000L});
    public static final BitSet FOLLOW_164 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_166 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_167 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_168 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_169 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_170 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_171 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_172 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0380000000000000L});
    public static final BitSet FOLLOW_173 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_174 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x1C00000000000000L});
    public static final BitSet FOLLOW_175 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_176 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_177 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_178 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_179 = new BitSet(new long[]{0x0000000000000000L,0x0000000000800000L,0x1C00000000000000L});
    public static final BitSet FOLLOW_180 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1C00000000000000L});
    public static final BitSet FOLLOW_181 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1800000000000000L});
    public static final BitSet FOLLOW_182 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_183 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_184 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0xE000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_185 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0xC000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_186 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_187 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_188 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_189 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_190 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_191 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000001C000000000L});
    public static final BitSet FOLLOW_192 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x00000000000007E0L});
    public static final BitSet FOLLOW_193 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x00000000000007C0L});
    public static final BitSet FOLLOW_194 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000780L});
    public static final BitSet FOLLOW_195 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000700L});
    public static final BitSet FOLLOW_196 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_197 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_198 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000003800000000L});
    public static final BitSet FOLLOW_199 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001000L});
    public static final BitSet FOLLOW_200 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_201 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_202 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00003E0000000000L});
    public static final BitSet FOLLOW_203 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_205 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0007C00000000000L});
    public static final BitSet FOLLOW_206 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000380000L});
    public static final BitSet FOLLOW_207 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000300000L});
    public static final BitSet FOLLOW_208 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_209 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_210 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000078020000L});
    public static final BitSet FOLLOW_211 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000007000000L});
    public static final BitSet FOLLOW_212 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_214 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000780000000L});
    public static final BitSet FOLLOW_215 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_216 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_217 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_218 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_219 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_220 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_221 = new BitSet(new long[]{0x0000000200000000L});
    public static final BitSet FOLLOW_222 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_223 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_224 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_225 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_226 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_228 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_229 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_230 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000003C00000000L});
    public static final BitSet FOLLOW_231 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000003800000000L});
    public static final BitSet FOLLOW_232 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_233 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_234 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_235 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x0000000000007C20L});
    public static final BitSet FOLLOW_236 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_237 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001FFFL});
    public static final BitSet FOLLOW_239 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000700000000000L});
    public static final BitSet FOLLOW_240 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_241 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_242 = new BitSet(new long[]{0x0000000000010000L,0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_243 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_245 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_247 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_248 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});

}