# Run the complementary studies

**Without participants:** use Study A below, followed by the
[automated maintenance alternative](maintenance-protocol.md). This measures
source-edit locality and incomplete-change outcomes. The participant experiment
in sections 4–6 is optional future work and must not be reported as executed.

Start with [protocol.md](protocol.md). The software prepares and records studies.
It does not supply participants, certify independent review, or manufacture
experimental results. Existing published observations are retained unchanged.

## 1. Prepare development materials

From **Command Prompt** in `C:\Users\bader\projects\mlops-dsl`:

```bat
.\.venv-evaluation\Scripts\python.exe -m pip install -r evaluation\studies\requirements.txt
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py prepare --out evaluation\studies\local\materials-01
powershell -NoProfile -File tools\Sync-EclipseWorkspace.ps1
```

Refresh the Eclipse test project and clean/build. The sync step copies the new
exporter as well as existing sources. This preparation creates 16 evolution
tasks, seven development faults and 39 actual-generator export jobs.

## 2. Execute the actual Xtext exports

Run `MlopsDslStudyExportTest` as JUnit 5. In **Run Configurations > Arguments >
VM arguments**, set:

```text
-Dmlops.study.jobs=C:\Users\bader\projects\mlops-dsl\evaluation\studies\local\materials-01\jobs.tsv
```

The exporter evaluates every job through the real loaded parser, validator and
generator. A passing exporter method means observations were saved. It does not
mean every generated configuration passed its external expectations. The Python
runner scores those expectations separately. Export JUnit XML from Eclipse.
Exports never overwrite an existing folder. Use a new materials run for retries.

Read `azure-port-A\before\dsl-export\receipt.json` and copy its
`generator_class_sha256` into a local copy of `config.pilot.json`. Keep `mode`
as `pilot` until review, calibration and recruitment planning are complete.

## 3. Freeze and run Study A

```bat
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py freeze --config evaluation\studies\local\config.pilot.json --materials evaluation\studies\local\materials-01 --out evaluation\studies\local\plan-pilot-01
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py study1 --plan evaluation\studies\local\plan-pilot-01 --out evaluation\studies\local\correctness-pilot-01
```

Kustomize must be reachable through `kubectl`. `--kubectl` accepts its executable
path. The report separates source acceptance, output correctness, preservation,
faults and blocked commands. The initial C00/C10 matrix remains a development
benchmark. Record additional project selection and scope reviews before making
broader claims. New configuration tasks require fresh real Xtext exports.

## 4. Calibrate Study B and justify recruitment

Recruit pilot participants from the intended population, give both tools equal
training, and record eight tasks per participant using the steps below. Use
anonymous identifiers only. Eight participants and a 15-minute time cap in the
pilot template are starting design choices, not a claim of adequate power.

After calibration, document the smallest practically useful score improvement
and the estimated SD of allocation-block contrasts. Run sensitivity planning
with your justified values, for example by substituting variables in PowerShell:

```powershell
& .\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py power `
  --delta $MinimumUsefulScoreImprovement --block-sd $PilotBlockStandardDeviation `
  --out evaluation\studies\local\power-assumptions-01.json
```

Do not use invented values as observed effects. Review several plausible SDs.
Complete a new configuration with `mode: confirmatory`, recruitment target,
reviewer, review record, minimum effect, rationale and power report path relative
to that configuration. Review every manifest item. Freeze a new plan before
collecting confirmatory observations. Exclude pilot participants from that sample.

## 5. Allocate and collect actual participant tasks

Create a private CSV with header `participant_id,stratum` and anonymous rows such
as `P001,intermediate`. Each stratum must contain a multiple of four participants.
Do not commit names, contact details or unfinished private participant sessions.

```bat
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py allocate --plan evaluation\studies\local\plan-pilot-01 --roster evaluation\studies\local\roster.csv --out evaluation\studies\local\allocation-pilot-01
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py start --allocation evaluation\studies\local\allocation-pilot-01\allocation.json --participant P001 --slot 1 --session evaluation\studies\local\sessions-pilot-01\P001\01
```

Start only when the participant is ready. The goal statement and working source
path are printed. Keep the examiner's expected-after files and scripts hidden
from participants. Edit only the working source. Each task begins afresh.

For a **baseline attempt**, run:

```bat
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py attempt --session evaluation\studies\local\sessions-pilot-01\P001\01
```

For a **DSL attempt**, export the participant's actual working model with these
VM arguments, instead of the batch `mlops.study.jobs` argument:

```text
-Dmlops.study.source=C:\...\sessions-pilot-01\P001\01\working\model.mlops
-Dmlops.study.output=C:\...\sessions-pilot-01\P001\01\export-01
```

Then score that export:

```bat
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py attempt --session evaluation\studies\local\sessions-pilot-01\P001\01 --export "C:\full\path\to\export-01"
```

Replace the abbreviated paths with actual paths. Keep the exporter launch
configuration ready for each participant to avoid measuring examiner navigation.
Use a new `export-02` path for a correction attempt. A correct attempt within the
limit closes the session. For deliberate abandonment or an infrastructure
interruption, use `close --session PATH --reason abandoned` or `infrastructure`.
Timeouts require that the recorded time limit has actually elapsed. Continue
allocated slots in order. Never delete failed attempts to improve the results.

## 6. Analyse complete observations

```bat
.\.venv-evaluation\Scripts\python.exe tools\controlled_studies.py analyse --allocation evaluation\studies\local\allocation-pilot-01\allocation.json --sessions evaluation\studies\local\sessions-pilot-01 --out evaluation\studies\local\analysis-pilot-01
```

Missing sessions and contradictory receipts block analysis. The output includes
the primary block-randomisation test, effect estimate, block-bootstrap interval,
secondary completion result and participant plot. It states pilot/confirmatory
status and exclusions. A report does not turn a convenience sample into a
representative population. Review the interval, practical effect and deviations
before wording manuscript conclusions.

## Instrument checks

```bat
.\.venv-evaluation\Scripts\python.exe -m unittest discover -s tests -p test_controlled_studies.py -v
```

Unit-test fixtures and power simulations are software verification, not measured
framework or participant results. No cloud credentials or apply commands are
needed for these studies.
