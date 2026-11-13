# VWO Login QA Project

This folder consolidates the VWO login requirements, RICE-POT prompts, test-case analysis, and Selenium/Playwright automation. Use this README as the project entry point.

## Project contents

| Folder/file | Purpose |
|---|---|
| [`PlaywrightFramework/`](./PlaywrightFramework/) | Existing TypeScript Playwright framework with Page Object Model |
| [`SeleniumFrameWork/`](./SeleniumFrameWork/) | Existing Java Selenium/TestNG framework |
| [`PlaywrightScriptWithAntiHallucination/`](./PlaywrightScriptWithAntiHallucination/) | RICE-POT baseline, anti-hallucination review stages, and runnable Playwright POM suite |
| [`SeleniumScriptWithAntiHallucination/`](./SeleniumScriptWithAntiHallucination/) | RICE-POT baseline, anti-hallucination review stages, and runnable Selenium POM suite |
| [`RequirementDoc/`](./RequirementDoc/) | VWO Login Dashboard PRD |
| [`Template_RICE_POT/`](./Template_RICE_POT/) | VWO Playwright and Selenium RICE-POT source prompts |
| [`anti-hallucination.rules.md`](./anti-hallucination.rules.md) | Rules used for the staged test-case review |
| [`RICE-POT Prompt — Playwright (TypeScript) Generic Template.md`](./RICE-POT%20Prompt%20%E2%80%94%20Playwright%20(TypeScript)%20Generic%20Template.md) | Generic reusable Playwright RICE-POT template |

## Staged test-case work

Each `*ScriptWithAntiHallucination/` folder contains:

1. `test-cases-ricepot-baseline.md` — Stage 1 baseline.
2. `test-cases-antihallucination-stage-2.md` — evidence-bounded Stage 2 candidates.
3. `information-gaps-stage-3.md` — Stage 3 information-gap inventory.
4. `test-case-comparison-stage-4.md` — Stage 4 baseline comparison and gap handling.
5. `test-case-accuracy-evaluation-stage-5.md` — Stage 5 accuracy evaluation.
6. The matching framework and README for the Stage 2 evidence-bounded checks.

The anti-hallucination Playwright suite was type-checked and passed **12/12** tests in Chromium. The Selenium suite passed **12/12** tests with TestNG. The suites do not submit login credentials or run authentication-failure tests; unresolved cases remain documented as deferred or blocked.

## Run the anti-hallucination suites

### Playwright

Requirements: Node.js and npm.

```bash
cd PlaywrightScriptWithAntiHallucination
npm install
npx playwright install chromium
npm run typecheck
npm test
```

### Selenium

Requirements: Java 17+, Maven, and Google Chrome.

```bash
cd SeleniumScriptWithAntiHallucination
mvn test
```

See each framework's README for its test scope and configuration.
