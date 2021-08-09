package com.cold0.realestatemanager.repository

import com.cold0.realestatemanager.model.Estate
import com.cold0.realestatemanager.model.EstateType
import com.cold0.realestatemanager.model.Photo

object DataProvider {

    private val loremIpsum: List<String> =
        listOf(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent pulvinar erat eget auctor ultricies. Vestibulum id purus iaculis, semper mauris id, mollis velit. Maecenas in tempor metus. Sed sed lectus tellus. Duis condimentum odio arcu, nec sodales nisl feugiat at. Nulla ut nisi eu lorem pulvinar efficitur. Nunc risus felis, fringilla et tempor is, convallis quis dolor. Mauris varius mattis imperdiet. Quisque ullamcorper erat ut dui tempus gravida. Maecenas laoreet et quam vel fringilla. Quisque sed libero varius, auctor augue non, viverra mi. Praesent cursus enim eu mauris suscipit ornare. In pulvinar nulla finibus ante ultrices, at rhoncus nulla tristique. Ut at sapien ac massa iaculis pharetra non quis metus. Morbi quis ullamcorper diam, sit amet blandit velit.",
            "Mauris euismod rutrum mauris, vitae tincidunt turpis fringilla vel. Praesent vehicula urna sed ligula dignissim, vitae rhoncus augue imperdiet. Etiam ac sem justo. Quisque condimentum tincidunt urna, non bibendum nisl. Donec sollicitudin tortor eget nisl ultrices, non mattis tellus varius. Curabitur ultricies lorem et nibh venenatis laoreet. Etiam orci tellus, tincidunt et iaculis sit amet, scelerisque ut velit. Donec et malesuada lectus. Maecenas elit lectus, consequat ultricies dictum aliquet, rutrum a tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Proin ornare ullamcorper volutpat. Vestibulum ligula augue, commodo ut condimentum nec, bibendum varius mauris.",
            "Pellentesque semper at eros convallis lobortis. Etiam quam mi, eleifend eget finibus eu, auctor vel metus. Aliquam leo enim, vulputate in justo at, condimentum viverra velit. Aenean ex turpis, laoreet ac mi ut, consectetur facilisis ligula. Morbi a maximus lorem. Curabitur vitae enim ullamcorper, fringilla tellus eu, tristique libero. Sed lectus mauris, pulvinar vitae interdum quis, bibendum ut urna. Donec dolor turpis, consequat eget tortor elementum, elementum blandit risus. Morbi blandit dolor vel lectus eleifend, ut auctor massa vehicula. Morbi aliquam arcu augue, sed mollis enim feugiat id. Aliquam vitae felis at elit laoreet cursus sit amet id nunc. Nam eget ex id magna elementum ultricies. Praesent tempus purus a lectus aliquam ultrices. Maecenas id felis fermentum, vestibulum sem eget, vulputate ipsum.",
            "Cras sit amet elementum neque. Nunc condimentum ex elit, ac posuere nulla pellentesque non. Suspendisse id aliquet eros. Praesent efficitur vulputate ullamcorper. Proin ultrices nibh felis, ut iaculis elit dignissim quis. Phasellus lobortis enim ornare, vulputate elit vel, pretium lorem. Donec leo erat, bibendum non tellus nec, ornare ultricies massa. Aenean elementum neque sit amet ornare bibendum. Donec ultrices nibh risus, dictum scelerisque lectus vestibulum et. Aliquam sem risus, semper eget facilisis nec, sollicitudin quis nisi. Etiam vel tincidunt ipsum. Curabitur eu dui eleifend, luctus ipsum lobortis, euismod leo. Vivamus porta luctus tortor in efficitur. Phasellus pulvinar tincidunt quam, vitae imperdiet magna aliquam at. Nulla ut mattis odio.",
            "Donec id magna massa. Nullam malesuada, massa eget lobortis viverra, quam mi ullamcorper nisi, consectetur commodo dolor ante ut odio. Donec pulvinar eget metus vel sodales. Nunc non sem sit amet sapien ornare facilisis. Nam tellus diam, ornare nec turpis dictum, convallis malesuada elit. In sagittis tellus convallis, euismod enim at, efficitur enim. Curabitur ornare condimentum ligula, a tempus tellus consectetur at. Nunc nec felis at tellus imperdiet consequat id ut nisl. Proin blandit odio vel laoreet ultricies. Proin non pretium elit.")

    val estateList: List<Estate> = listOf(
        Estate(
            district = "Manhattan",
            type = EstateType.Flat,
            description = loremIpsum[0],
            price = 17870000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/81/400"),
                Photo("Kitchen", "https://picsum.photos/id/5/400"),
                Photo("Bedroom", "https://picsum.photos/id/6/400")
            )
        ),
        Estate(
            district = "Montauk",
            type = EstateType.House,
            description = loremIpsum[1],
            price = 21130000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/82/400"),
                Photo("Kitchen", "https://picsum.photos/id/7/400"),
                Photo("Bedroom", "https://picsum.photos/id/8/400")
            )

        ),
        Estate(
            district = "Brooklyn",
            type = EstateType.Duplex,
            description = loremIpsum[2],
            price = 13990000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/83/400"),
                Photo("Kitchen", "https://picsum.photos/id/9/400"),
                Photo("Bedroom", "https://picsum.photos/id/10/400")
            )
        ),
        Estate(
            district = "Southampton",
            type = EstateType.House,
            description = loremIpsum[3],
            price = 41480000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/84/400"),
                Photo("Kitchen", "https://picsum.photos/id/11/400"),
                Photo("Bedroom", "https://picsum.photos/id/12/400")
            )
        ),
        Estate(
            district = "Upper East Side",
            type = EstateType.Penthouse,
            description = loremIpsum[4],
            price = 29872000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/85/400"),
                Photo("Kitchen", "https://picsum.photos/id/13/400"),
                Photo("Bedroom", "https://picsum.photos/id/14/400")
            )
        ),
        Estate(
            district = "Manhattan",
            type = EstateType.Flat,
            description = loremIpsum[3],
            price = 17870000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/87/400"),
                Photo("Kitchen", "https://picsum.photos/id/88/400"),
                Photo("Bedroom", "https://picsum.photos/id/100/400")
            )
        ),
        Estate(
            district = "Montauk",
            type = EstateType.House,
            description = loremIpsum[2],
            price = 21130000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/89/400"),
                Photo("Kitchen", "https://picsum.photos/id/15/400"),
                Photo("Bedroom", "https://picsum.photos/id/100/400")
            )
        ),
        Estate(
            district = "Brooklyn",
            type = EstateType.Duplex,
            description = loremIpsum[4],
            price = 13990000,
            pictures = listOf(
                Photo("Facade", "https://picsum.photos/id/33/400"),
                Photo("Kitchen", "https://picsum.photos/id/16/400"),
                Photo("Bedroom", "https://picsum.photos/id/17/400")
            )
        ),
        Estate(
            district = "Southampton",
            type = EstateType.House,
            description = loremIpsum[1],
            price = 41480000,
            pictures = listOf(
                Photo("Facade 1", "https://picsum.photos/id/450/400"),
                Photo("Facade 2", "https://picsum.photos/id/451/400"),
                Photo("Kitchen", "https://picsum.photos/id/452/400"),
                Photo("Bedroom 1", "https://picsum.photos/id/453/400"),
                Photo("Bedroom 2", "https://picsum.photos/id/454/400"),
            )
        ),
        Estate(
            district = "Upper East Side",
            type = EstateType.Penthouse,
            description = loremIpsum[0],
            price = 29872000,
            pictures = listOf(
                Photo("Facade 1", "https://picsum.photos/id/350/400"),
            )
        )
    )
}