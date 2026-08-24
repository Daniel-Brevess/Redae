import type { InputHTMLAttributes } from 'react'

type FormFieldProps = InputHTMLAttributes<HTMLInputElement> & { label: string }

export function FormField({ id, label, ...props }: FormFieldProps) {
  return <><label htmlFor={id}>{label}</label><input id={id} {...props} /></>
}
