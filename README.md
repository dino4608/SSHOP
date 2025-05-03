# Project Documentation

<br></br>
## 🎮 **Shortcuts for VSCode and IntelliJ IDEA**

| **Action**                           | **VSCode**             | **IntelliJ IDEA**      |
|--------------------------------------|------------------------|------------------------|
| **Clear Redundant Imports**          | `Shift + Alt + O`      | `Ctrl + Alt + O`       |
| **Format Code**                      | `Ctrl + S`             | `Ctrl + Alt + L`       |
| **Review README file**               | `Ctrl + Shift + V`     |                        |
| **Find and Replace**                 | `Ctrl + Shift + F`     | `Ctrl + Shift + F / R` |
| **Open File**                         | `Ctrl + P`             | `Ctrl + Shift + N`     |
| **Rename & Refactor**                | `F2`                   | `Shift + F6`           |
| **Window Icon**                      | `Window + .`           |                        |

<br></br>
## 🐞 **Faced Bugs and Issues**

| **Issue**                                                        | **Explanation**                                                                 |
|------------------------------------------------------------------|---------------------------------------------------------------------------------|
| **Client component calling server component**                   | ❌ Not allowed. Keep in mind the flow from server to client.                    |
| **Client component calling server action**                      | ✅ Allowed only in Next.js forms.                                                |
| **Defining a server action in a server action**                  | ❌ Not allowed.                                                                 |
| **Next server component vs. server action**                     | Dynamic UI on server vs Handling logic on server; no directive vs `use server`. |
| **Unused variable when building**                                | ❌ Configure ESLint to avoid this.                                              |
| **Transparent vs. Transition in CSS**                            | Transparent: Opacity; Transition: Smooth effect.                                |
| **CSS Border vs. Ring vs. Outline**                              | Border: Inside the element; Ring: Outside; Outline: On top of the box.         |
| **Repository and form data in #Postman**                         | ❓ Refer to Postman for API requests or testing forms.                         |

<br></br>
## ⚠️ **Advises**

| **Advice**                                           | **Explanation**                                                                 |
|------------------------------------------------------|---------------------------------------------------------------------------------|
| **GET requests should not have a body**              | Technically, GET requests should not send data in the body. Use query params.   |
| **Postman: POST should use raw**                     | POST should use raw, instead of form-data                                       |

<br></br>
## 🏃‍♂️ **How to Run the Buyer App**

1. Open the **command line** (CMD).
2. Run the following commands:
   ```bash
   cd apps/buyer
   npm install
   npm run dev
   ```

<br></br>
## 🚀 **Deploy**

**Next.js Buyer App Deployment**

- Local: [localhost:3000](http://localhost:3000)   => Vercel: [buyer-deal.vercel.app](https://buyer-deal.vercel.app)

**Spring Boot Backend Deployment**

- Local: [localhost:8080](http://localhost:8080)   => Ngrok: [cheetah-dear-mutt.ngrok-free.app](https://cheetah-dear-mutt.ngrok-free.app)

<br></br>
## ⛓️‍💥 **Ngrok**

**Ngrok** is a tool that creates a secure tunnel from a public endpoint to your local machine, which is great for exposing your locally running services for external testing or APIs.

- **Public Domain:** `https://cheetah-dear-mutt.ngrok-free.app/api/v1/`

#### **Common Ngrok Commands:**

1. Start Ngrok with a configuration file: `ngrok start --config=ngrok.yml backend`

2. Start Ngrok with the port 8080: `http 8080`

3. Get the address of ngrok.yml: `ngrok config check`


