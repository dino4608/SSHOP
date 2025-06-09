import { startTransition, useActionState } from "react";
import { DefaultValues, FieldValues, Resolver, useForm, UseFormReturn } from "react-hook-form";

type TState = { message: string; } | undefined;

type TFormActionStateInput<TFormData extends FieldValues> = {
    resolver: Resolver<TFormData, any, TFormData>,
    action: (prevState: TState, formData: FormData) => Promise<TState>,
    initState: TState,
    defaultValues: DefaultValues<TFormData>
}

type TFormActionStateOutput<TFormData extends FieldValues> = {
    form: UseFormReturn<TFormData, any, TFormData>
    state: TState
    isPending: boolean
    handleSubmit: (data: TFormData) => void
}

const useFormActionState = <TFormData extends Record<string, any>>({
    resolver,
    action,
    initState,
    defaultValues,
}: TFormActionStateInput<TFormData>): TFormActionStateOutput<TFormData> => {

    const form = useForm<TFormData>({
        resolver,
        defaultValues,
    })

    const [state, submitAction, isPending] = useActionState(action, initState)

    const handleSubmit = (data: TFormData) => {
        const formData = new FormData()
        Object.entries(data).forEach(([key, value]) => {
            formData.append(key, value as string)
        })

        startTransition(() => {
            submitAction(formData)
        })
    }

    return {
        form,
        state,
        isPending,
        handleSubmit,
    }
}

export default useFormActionState;
