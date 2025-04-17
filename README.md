Helpful shortcuts:

- Shift + Alt + O: to clear redundant imports

- Ctrl + Shift + V: to review the README file

Faced Bugs:

- A client component call a server component: X => Be not allowed. Keep in mind the flow from server to client.

- A client component call a server action to apply in the form: O => This is only way that Next allowing. 'use server' generate a server action.

- Define a server action in a server component: X => Be not allowed. No directive generate a server component.

- Expected any, Unused variable when building: X => Config eslint