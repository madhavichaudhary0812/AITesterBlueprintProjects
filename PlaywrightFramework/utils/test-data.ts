export type LoginCredentials = {
  email: string;
  password: string;
};

export function getValidCredentials(): LoginCredentials | undefined {
  const email = process.env.VWO_EMAIL?.trim();
  const password = process.env.VWO_PASSWORD;

  if (!email || !password) {
    return undefined;
  }

  return { email, password };
}
