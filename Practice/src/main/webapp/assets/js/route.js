const router = new VueRouter({
    mode: 'history',
    routes: [
        { path: '/index',
            // a single route can define multiple named components
            // which will be rendered into <router-view>s with corresponding names.
            components: {
                // default: Foo,
                merchantCreate: { template: '#createMerchantView' },
                // b: Baz
            }
        },
        {
            path: '/merchant/create',
            components: {
                // default: Baz,
                merchantCreate: { template: '#createMerchantTemp' },
                // b: Foo
            }
        }
    ]
})