Helpful shortcuts on VSCODE and Intellij IDEA:

- To clear redundant imports:
  - Shift + Alt + O
  - Ctrl + Alt + O
- To format code:
  - Ctrl + S
  - Ctrl + Alt + L
- To review the README file:
  - Ctrl + Shift + V
  - ...
- To find and replace a text:
  - Ctrl + Shift + F
  - Ctrl + Shift + F / R
- To open a file:
  - Ctrl + P
  - Ctrl + Shift + N
- To rename and refactor:
  - F2
  - Shift + F6

Faced Bugs:

- A client component call a server component: X => Be not allowed. Keep in mind the flow from server to client.

- A client component call a server action: O => This is only way that Next allowing and can only apply on form.

- Define a server action in a server action: X => Be not allowed.

- Next server component vs server action: Dynamic UI on server vs Handling logic on server; no directive vs 'use server'.

- Expected any, Unused variable when building: X => Config eslint

- CSS transparent vs transition: trong suốt vs mượt mà

- CSS border vs ring vs outline: trong vs ngoài vs trên box

- or #repo, form data #postman

Advises:

- GET requests technically should not have a body

How to run:

- About the buyer app, open cmd, run:
  - cd apps/buyer
  - npm install
  - npm run dev

Deploy:
- Next.js buyer app: localhost:3000 ===> Vercel: buyer-deal.vercel.app
- Spring boot backend: localhost:8080 ===> Ngrok: cheetah-dear-mutt.ngrok-free.app

Ngrok:
- Description: this tool can create a tunnel to public the local server.
- Public domain: cheetah-dear-mutt.ngrok-free.app/api/v1 (+/endpoint)
- Common command:
  - ngrok start --config=ngrok.yml backend: to public the local backend in the yml file
  - ngrok http 8080: to public the 8080 local backend
  - ngrok config check: to get the address of ngrok.yml