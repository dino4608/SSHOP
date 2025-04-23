import { cookies } from 'next/headers';
import { NextRequest, NextResponse } from 'next/server'

// 1. Specify public routes
const publicRoutes = ['/', '/sign-in', '/sign-up', '/test', '/auth']

export default async function middleware(request: NextRequest) {
    // 2. Check if the current route is public
    const path = request.nextUrl.pathname
    const isPublicRoute = publicRoutes.includes(path)

    // temporarily allow access to all routes
    if (path.startsWith('/')) {
        return NextResponse.next()
    }

    // 3. Get tokens from the cookie and storage
    const ACCESS_TOKEN = (await cookies()).get('ACCESS_TOKEN')?.value // or undefined
    // 4. Redirect to /login if the user is not authenticated
    if (!isPublicRoute && !ACCESS_TOKEN) {
        return NextResponse.redirect(new URL('/login', request.nextUrl))
    }

    // 5. Redirect to / (home) if the user is authenticated
    if (
        isPublicRoute &&
        ACCESS_TOKEN &&
        !path.match('/')
    ) {
        return NextResponse.redirect(new URL('/', request.nextUrl))
    }

    return NextResponse.next()
}

export const config = {
    matcher: [
        /*
         * Match all request paths except for the ones starting with:
         * - api (API routes)
         * - _next/static (static files)
         * - _next/image (image optimization files)
         * - favicon.ico, sitemap.xml, robots.txt (metadata files)
         * - files.svg (public files)
         */
        '/((?!api|_next/static|_next/image|favicon.ico|sitemap.xml|robots.txt|.*\\.svg$|.*\\.jpg$).*)',
    ],
}
