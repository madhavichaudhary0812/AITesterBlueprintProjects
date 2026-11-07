import { test as base, expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { LoginPage } from '../pages/LoginPage';

type TestFixtures = {
  loginPage: LoginPage;
  allureLabels: void;
};

export const test = base.extend<TestFixtures>({
  loginPage: async ({ page }, use) => {
    await use(new LoginPage(page));
  },
  allureLabels: [
    async ({}, use, testInfo) => {
      const testCaseId = testInfo.title.match(/TC-[PN]\d{2}/)?.[0];
      if (testCaseId) {
        await allure.label('testCaseId', testCaseId);
      }

      const requirements = new Set(
        testInfo.title.match(/REQ-[A-Z0-9-]+/g) ?? []
      );
      for (const requirement of requirements) {
        await allure.label('requirement', requirement);
      }

      await use();
    },
    { auto: true }
  ]
});

export { expect };
