package com.daniebeler.pixelix.data.remote.dto

import com.daniebeler.pixelix.domain.model.Instance
import com.daniebeler.pixelix.domain.model.toAccount
import com.daniebeler.pixelix.domain.model.toRule
import com.google.gson.annotations.SerializedName

data class InstanceDto(
    @SerializedName("uri")
    val domain: String,
    @SerializedName("rules")
    val rules: List<RuleDto>,
    @SerializedName("short_description")
    val shortDescription: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnailUrl: String,
    @SerializedName("contact_account")
    val admin: AccountDto,
    @SerializedName("stats")
    val stats: InstanceStatsDto,
    @SerializedName("version")
    val version: String

) : DtoInterface<Instance> {
    override fun toModel(): Instance {
        return Instance(
            domain = domain,
            rules = rules.map { it.toRule() },
            shortDescription = shortDescription,
            thumbnailUrl = thumbnailUrl,
            description = description,
            admin = admin.toAccount(),
            stats = stats.toModel(),
            version = version
        )
    }
}
