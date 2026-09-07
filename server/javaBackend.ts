import { ENV } from "./_core/env";

const DEFAULT_TIMEOUT_MS = 2500;

async function requestJava<T>(path: string, init: RequestInit = {}, timeoutMs = DEFAULT_TIMEOUT_MS): Promise<T> {
  const controller = new AbortController();
  const timeout = setTimeout(() => controller.abort(), timeoutMs);

  try {
    const response = await fetch(`${ENV.javaBackendUrl}${path}`, {
      ...init,
      headers: {
        "Content-Type": "application/json",
        ...(init.headers ?? {}),
      },
      signal: controller.signal,
    });

    if (!response.ok) {
      throw new Error(`Java backend returned HTTP ${response.status}`);
    }

    return (await response.json()) as T;
  } finally {
    clearTimeout(timeout);
  }
}

export type JavaGradeInput = {
  courseCode?: string;
  courseName?: string;
  percentage: number;
  credits: number;
};

export type JavaEvaluationResponse = {
  gpa: number;
  averagePercentage: number;
  academicStanding: string;
  calculationPolicy: string;
  courseCount: number;
  totalCredits: number;
};

export async function calculateGpaWithJava(
  studentType: "engineering" | "management" | "graduate",
  grades: JavaGradeInput[],
): Promise<JavaEvaluationResponse> {
  return requestJava<JavaEvaluationResponse>("/api/evaluation/gpa", {
    method: "POST",
    body: JSON.stringify({ studentType, grades }),
  });
}

export async function validateGradeWithJava(percentage: number): Promise<{
  valid: boolean;
  percentage: number;
  gpa: number;
  letter: string;
  passing: boolean;
  band: string;
}> {
  return requestJava(`/api/evaluation/grade-scale?percentage=${encodeURIComponent(percentage)}`);
}

export async function getJavaHealth(): Promise<Record<string, unknown>> {
  return requestJava<Record<string, unknown>>("/health");
}
