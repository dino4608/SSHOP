import { NextRequest, NextResponse } from 'next/server';
import { asyncIsAuthenticated } from './lib/server/auth';

// 1. Specify public routes
const publicStaticRoutes = ['/', '/auth', '/auth/google']
const publicDynamicPrefixes = ['/product/']
const explicitlyPrivateRoutes = ['/product/test'];

// isPublicStatic: oke, ko cần check dynamic route
// !isPublicStatic: !isPublicDynamic: oke, ko cần check isExplicitlyPrivate
// !isPublicStatic: isPublicDynamic: rủi ro, cần check isExplicitlyPrivate
function isPublicRoute(path: string): boolean {
    const isPublicStatic = publicStaticRoutes.includes(path);
    if (isPublicStatic) return true;

    const isPublicDynamic = publicDynamicPrefixes.some(prefix => path.startsWith(prefix));
    if (!isPublicDynamic) return false;

    const isExplicitlyPrivate = explicitlyPrivateRoutes.includes(path);
    return !isExplicitlyPrivate;
}

export default async function middleware(request: NextRequest) {
    // 2. Check if the current route is public
    const path = request.nextUrl.pathname
    const isPublic = isPublicRoute(path);

    // 3. Get tokens from the cookie and storage
    const isAuthenticated = await asyncIsAuthenticated()

    // 4. Redirect to /auth if the user is not authenticated
    if (!isPublic && !isAuthenticated) {
        console.warn(`>>> Middleware: path ${path}: isPublic ${isPublic} `);
        return NextResponse.redirect(new URL('/', request.nextUrl))
    }

    // 5. Pass
    // Case 1: isPublic => pass
    // Case 2: !isPublic + isAuthenticated => pass
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
