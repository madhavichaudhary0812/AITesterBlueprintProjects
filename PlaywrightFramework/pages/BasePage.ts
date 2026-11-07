import type { Page } from '@playwright/test';

export class BasePage {
  protected readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  protected async navigateTo(path: string): Promise<void> {
    await this.withErrorContext(`Navigate to ${path}`, async () => {
      await this.page.goto(path);
    });
  }

  protected async withErrorContext<T>(
    action: string,
    operation: () => Promise<T>
  ): Promise<T> {
    try {
      return await operation();
    } catch (error) {
      const detail = error instanceof Error ? error.message : String(error);
      throw new Error(`${action} failed: ${detail}`, { cause: error });
    }
  }
}
