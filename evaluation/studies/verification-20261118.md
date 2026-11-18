# Instrument verification, 18 November 2026

The complete Python suite ran 50 tests successfully, with no skips, using
Python 3.12, NumPy 2.3.5, PyYAML 6.0.3, python-hcl2 7.3.1 and kubectl 1.35.4.
The command was:

```sh
MLOPS_TEST_KUBECTL=/tmp/mlops-study-tools/kubectl python -m unittest discover -s tests -v
```

The new study-specific tests cover allocation balance, whole-block analysis,
known exact randomisation probabilities, bootstrap reproducibility, explicit
planning assumptions, preserved unsuccessful outcomes, source/output integrity,
unreviewed-plan rejection and semantic YAML preservation. The integration check
builds both phases of all 16 new evolution tasks through actual Kustomize,
checks their contracts and tests for unintended selected YAML changes. Existing
GCP native-build integration also ran.

These are software/instrument checks. They are not newly collected participant
observations or an independent application sample. The Java exporter has not
been compiled or run in the current execution environment, which lacks the
project's complete Eclipse/metamodel build target. Execute it in the existing
Eclipse environment and retain its JUnit XML and real export receipts before
running Study A or starting participant collection. No Terraform validation,
cloud apply or statistical claim about human productivity was executed here.

The LaTeX manuscript and published observations were not modified by this change.

## Windows fixture correction

A subsequent author-side Windows run passed 49 checks and failed one GCP
integrity-test setup because a text-mode fixture write translated LF to CRLF.
The synthetic test source now uses explicit UTF-8 byte writes. Two regression
checks simulate Windows text newline translation and verify that a genuinely
changed source is still rejected by the unchanged importer. The complete suite
then passed 52 tests locally with real Kustomize integration. This local check
simulates the relevant Windows I/O behaviour and is not a Windows-host rerun.

## Automated maintenance alternative

After the author reported that participants were unavailable, an automated
edit-subset experiment was added. Its instrument checks cover token-level
value substitutions, exact subset enumeration, preservation of starting source,
locked-input rejection, endpoint controls and a real Kustomize example where
an apparently duplicated domain edit is unnecessary. All 62 Python tests passed
locally with the native Kustomize checks enabled. No partial-model Xtext exports
or human observations were fabricated for this verification. The prepared
workload has 168 DSL inputs and 44 baseline inputs across the same 16 change
instances. Actual execution of those DSL inputs remains a separate step.
