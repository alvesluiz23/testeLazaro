import { HttpErrorResponse } from '@angular/common/http';

type ValidationErrorBody = {
  message?: string;
  errors?: unknown;
};

export function errorToAlertMessage(error: unknown): string {
  if (error instanceof HttpErrorResponse) {
    const body = error.error as unknown;

    if (typeof body === 'string' && body.trim() !== '') {
      return body;
    }

    if (body && typeof body === 'object') {
      const maybe = body as ValidationErrorBody;
      const message = typeof maybe.message === 'string' ? maybe.message : undefined;

      if (Array.isArray(maybe.errors)) {
        const details = maybe.errors
          .map((x) => (typeof x === 'string' ? x : JSON.stringify(x)))
          .filter((x) => x && x !== 'null' && x !== '""');

        if (details.length > 0) {
          return [message ?? 'Validation failed', ...details.map((d) => `- ${d}`)].join('\n');
        }
      }

      if (message && message.trim() !== '') {
        return message;
      }
    }

    if (error.message && error.message.trim() !== '') {
      return error.message;
    }

    const statusLine = [error.status, error.statusText].filter(Boolean).join(' ');
    return statusLine || 'Request failed';
  }

  if (error instanceof Error && error.message.trim() !== '') {
    return error.message;
  }

  return 'Unexpected error';
}

