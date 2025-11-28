# Frontend Setup Guide

## Environment Variables

Create a `.env` file in the frontend directory with the following variables:

```env
# Google reCAPTCHA Site Key (Optional)
# Get your site key from https://www.google.com/recaptcha/admin
# Leave empty to disable reCAPTCHA during development
VITE_RECAPTCHA_SITE_KEY=your_site_key_here
```

## Getting a ReCAPTCHA Site Key

1. Go to [Google reCAPTCHA Admin](https://www.google.com/recaptcha/admin)
2. Click on the "+" button to create a new site
3. Fill in the form:
   - **Label**: Your app name (e.g., "Grievance Management System")
   - **reCAPTCHA type**: reCAPTCHA v2 → "I'm not a robot" Checkbox
   - **Domains**: Add `localhost` for development
4. Accept the terms and submit
5. Copy the **Site Key** and paste it in your `.env` file

## Development Without ReCAPTCHA

The login page will work without ReCAPTCHA if the `VITE_RECAPTCHA_SITE_KEY` is not set or is empty. This is useful for local development and testing.

## Installation

```bash
npm install
```

## Running the Development Server

```bash
npm run dev
```

The app will be available at `http://localhost:5173`

## Building for Production

```bash
npm run build
```

## Notes

- The `.env` file is git-ignored and should not be committed to version control
- Make sure to set up the environment variables in your production environment
- For production, add your production domain to the reCAPTCHA admin console
