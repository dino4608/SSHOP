import { NextRequest, NextResponse } from 'next/server';
import { asyncIsAuthenticated } from './lib/server/auth';

// 1. Specify public routes
const publicRoutes = ['/', '/auth']

export default async function middleware(request: NextRequest) {
    // 2. Check if the current route is public
    const isPublic = publicRoutes.includes(request.nextUrl.pathname)

    // 3. Get tokens from the cookie and storage
    const isAuthenticated = await asyncIsAuthenticated()

    // temporarily allow access to all routes
    // if (request.nextUrl.pathname.startsWith('/')) {
    //     return NextResponse.next()
    // }

    // 4. Redirect to /auth if the user is not authenticated
    if (!isPublic && !isAuthenticated) {
        return NextResponse.redirect(new URL('/auth', request.nextUrl))
    }

    // 5. Next
    // Case 1: isPublic => next
    // Case 2: !isPublic + isAuthenticated => next
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
         * - public/ file svg and jpg
         * - public/ images/
         */
        '/((?!api|_next/static|_next/image|favicon.ico|sitemap.xml|robots.txt|.*\\.svg$|.*\\.jpg$|images).*)',
    ],
}
