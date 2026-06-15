package university.miva.guest.ui.components

import Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import university.miva.designsystem.theme.MivaColors
import university.miva.guest.ui.viewmodel.models.FacultyInfo
import university.miva.guest.ui.viewmodel.models.facultyDescription
import university.miva.designsystem.R as DesignSystemR

@Composable
fun AboutFacultyScreen(
    faculty: FacultyInfo,
    onDismiss: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(topStart = Theme.spacing.x4, topEnd = Theme.spacing.x4),
        color = Color.White,
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 50.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .padding(bottom = 26.dp)
                    .verticalScroll(rememberScrollState()),
        ) {
            Spacer(
                modifier =
                    Modifier.padding(vertical = Theme.spacing.x1),
            )
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = Theme.spacing.x1, horizontal = Theme.spacing.x3),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(DesignSystemR.string.about_this_faculty),
                    style =
                        Theme.typography.h4.copy(
                            color = MivaColors.TextColors.BlueDeep,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 20.sp,
                        ),
                    textAlign = TextAlign.Start,
                )
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(DesignSystemR.string.close),
                        tint = MivaColors.TextColors.BlueDeep,
                    )
                }
            }
            Spacer(modifier = Modifier.height(Theme.spacing.x2))
            HorizontalDivider(color = MivaColors.Secondary.DividerColor)
            Spacer(modifier = Modifier.height(Theme.spacing.x6))

            if (faculty.image.isNullOrBlank().not()) {
                val painter = rememberAsyncImagePainter(model = faculty.image)

                Image(
                    painter = painter,
                    contentDescription = stringResource(DesignSystemR.string.info_icon),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(horizontal = Theme.spacing.x2),
                    contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.height(Theme.spacing.x2))
            } else {
                faculty.pageImage?.let {
                    Image(
                        painter = painterResource(faculty.pageImage),
                        contentDescription = stringResource(DesignSystemR.string.info_icon),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Theme.spacing.x2),
                    )
                    Spacer(modifier = Modifier.height(Theme.spacing.x2))
                }
            }

            Text(
                text =
                    facultyDescription.find { it.facultyId == faculty.facultyId }?.description
                        ?: faculty.description.orEmpty(),
                modifier = Modifier.padding(horizontal = Theme.spacing.x4, vertical = Theme.spacing.x4),
                style =
                    Theme.typography.bodyM.copy(
                        color = MivaColors.TextColors.Blue400,
                        lineHeight = 24.sp,
                    ),
                textAlign = TextAlign.Start,
            )

            Spacer(modifier = Modifier.height(Theme.spacing.x2))
        }
    }
}
