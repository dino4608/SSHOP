export function formatPrice(value: number | string, withSymbol = true): string {
    const number = typeof value === 'string' ? parseFloat(value) : value;

    if (isNaN(number)) return '0₫';

    const formatted = number.toLocaleString('vi-VN');
    return withSymbol ? `${formatted}₫` : formatted;
}

export function formatPercent(value: number | string, withMinus = true): string {
    const number = typeof value === 'string' ? parseFloat(value) : value;
    return withMinus ? `-${number}%` : `${number}%`;
}