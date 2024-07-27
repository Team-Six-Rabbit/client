import { handlers as authHandlers } from "@/mocks/auth/handler";
import { handlers as userHandlers } from "@/mocks/user/handler";

export const handlers = [...authHandlers, ...userHandlers];
