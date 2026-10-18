package org.xtext.ucd.mlopsdsl.scoping;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

import mlops.*; // importe MlTask, MlWorkflow, etc.

public class MlopsNameProvider extends DefaultDeclarativeQualifiedNameProvider {

  QualifiedName qualifiedName(ProjectSpec o)      { return q(o.getName()); }
  QualifiedName qualifiedName(FeatureSet o)       { return q(o.getName()); }
  QualifiedName qualifiedName(DataAsset o)        { return q(o.getLocation()); }
  QualifiedName qualifiedName(TrainingSpec o)     { return q(o.getFramework()); }
  QualifiedName qualifiedName(EvaluationSpec o)   {
    return (o.getMetrics().isEmpty() ? null : q(o.getMetrics().get(0)));
  }
  QualifiedName qualifiedName(RegistrySpec o)     { return q(o.getKind()); }

  QualifiedName qualifiedName(MlTask o)           { return q(o.getTaskId()); }

  QualifiedName qualifiedName(ServingSpec o)      {
	  return q(o.getName());
  }

  QualifiedName qualifiedName(MonitoringSpec o)   { return q("monitoring"); }
  QualifiedName qualifiedName(FeatureStoreSpec o) { return q(o.getOfflineStore()); }
  QualifiedName qualifiedName(OperationalPlan o)  { return q(o.getRationale()); }
  QualifiedName qualifiedName(Artifact o)         { return q(o.getPath()); }
  QualifiedName qualifiedName(InfraPlan o) {
	    return q(o.getClusterName());
	}


  private QualifiedName q(String s) {
    if (s==null) return null;
    String key = s.trim();
    if (key.isEmpty()) return null;
    return QualifiedName.create(key);
  }
}
