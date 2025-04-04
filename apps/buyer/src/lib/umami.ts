import umami from '@umami/node';

umami.init({
    websiteId: '6cd7e0bb-7072-4fd5-98c3-c5d2b7c997f6',
    hostUrl: 'https://cloud.umami.is', // URL to your Umami instance
});

export const umamiTrackCheckoutSuccessEvent = async (payload: {
    [key: string]: string | number | Date
}) => {
    await umami.track('checkout_success', payload);
}