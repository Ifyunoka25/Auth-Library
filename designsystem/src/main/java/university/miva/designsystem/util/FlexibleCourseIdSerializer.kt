package university.miva.designsystem.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull

object FlexibleCourseIdSerializer : KSerializer<String?> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("FlexibleCourseId", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): String? {
        // If this comes from JSON, we can inspect the actual JSON type
        if (decoder is JsonDecoder) {
            return when (val element: JsonElement = decoder.decodeJsonElement()) {
                is JsonNull -> null
                is JsonPrimitive -> {
                    when {
                        element.isString -> element.content // e.g "115"
                        element.intOrNull != null -> element.int.toString() // convert 1 -> "1"
                        element.longOrNull != null -> element.long.toString()
                        else -> element.content
                    }
                }
                else -> element.toString()
            }
        }

        // Fallback for non-JSON sources
        return decoder.decodeString()
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun serialize(
        encoder: Encoder,
        value: String?,
    ) {
        if (value == null) {
            encoder.encodeNull()
        } else {
            // always emit as string; that’s safe for your use-case
            encoder.encodeString(value)
        }
    }
}
