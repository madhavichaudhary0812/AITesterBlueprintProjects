# VWO Login - Playwright Anti-Hallucination Suite

This TypeScript Playwright project uses a Page Object Model and covers the evidence-bounded AH-P01 through AH-P12 checks from `test-cases-antihallucination-stage-2.md`.

The suite uses the specified VWO login URL and supplied XPath locators. It does not submit the login form, use credentials, open external identity-provider flows, or assert Remember me checkbox state.

## Requirements

- Node.js 18 or later
- npm

## Install and run

```bash
npm install
npx playwright install chromium
npm run typecheck
npm test
```

Run the browser visibly with `npm run test:headed`.

The Markdown files describe test cases and evaluation results; they are not executable Playwright test files. Run the implemented tests from this project directory with `npm test`, or open `tests/anti-hallucination-login.spec.ts` and use `npx playwright test tests/anti-hallucination-login.spec.ts`. To run one case, add `--grep "AH-P01"` (replace the ID as needed).

The suite is configured for Chromium. AH-P12 checks only that the configured URL string uses `https:`; it does not verify server-side HTTPS enforcement.
