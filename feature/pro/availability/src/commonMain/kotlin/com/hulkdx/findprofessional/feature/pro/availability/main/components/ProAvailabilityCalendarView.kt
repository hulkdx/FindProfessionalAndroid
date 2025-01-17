package com.hulkdx.findprofessional.feature.pro.availability.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hulkdx.findprofessional.core.resources.Res
import com.hulkdx.findprofessional.core.resources.ic_calendar_left
import com.hulkdx.findprofessional.core.resources.ic_calendar_right
import com.hulkdx.findprofessional.core.ui.theme.body1
import com.hulkdx.findprofessional.core.ui.theme.body1Medium
import com.hulkdx.findprofessional.core.ui.theme.h3Medium
import com.hulkdx.findprofessional.core.utils.DateUtils
import com.hulkdx.findprofessional.core.utils.DateUtils.weekNumberMap
import com.hulkdx.findprofessional.core.utils.singleClick
import com.hulkdx.findprofessional.feature.pro.availability.main.model.ProAvailabilityState
import kotlinx.datetime.DateTimeUnit.Companion.MONTH
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProAvailabilityCalendarView(
    now: LocalDate,
    availabilities: List<LocalDate>,
    onDateClicked: (LocalDate) -> Unit,
) {
    var date by remember { mutableStateOf(now) }
    val state by remember(date) {
        mutableStateOf(
            ProAvailabilityState(
                currentMonth = DateUtils.formatToMonthsAndYear(date),
                firstDay = DateUtils.firstDayInt(date),
                lengthOfMonth = DateUtils.lengthOfMonth(date),
            )
        )
    }
    val isSelectedDay: @Composable (Int) -> Boolean = { day ->
        remember(day, date, availabilities) {
            val checkDay = LocalDate(date.year, date.month, day)
            availabilities.contains(checkDay)
        }
    }

    AvailabilityCalendar(
        state,
        availabilityMonthMinusOne = {
            date = date.minus(1, MONTH)
        },
        availabilityMonthPlusOne = {
            date = date.plus(1, MONTH)
        },
        isSelectedDay = isSelectedDay,
        onDateClicked = singleClick<Int> {
            onDateClicked(LocalDate(date.year, date.month, it))
        },
    )
}


@Composable
private fun AvailabilityCalendar(
    state: ProAvailabilityState,
    availabilityMonthMinusOne: () -> Unit,
    availabilityMonthPlusOne: () -> Unit,
    isSelectedDay: @Composable (Int) -> Boolean,
    onDateClicked: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .statusBarsPadding()
            .padding(bottom = 32.dp, top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        AvailabilityCalendarTopHeader(
            state,
            availabilityMonthMinusOne,
            availabilityMonthPlusOne
        )
        AvailabilityCalendarMainContent(state, isSelectedDay, onDateClicked)
    }
}

@Composable
private fun AvailabilityCalendarTopHeader(
    availability: ProAvailabilityState,
    availabilityMonthMinusOne: () -> Unit,
    availabilityMonthPlusOne: () -> Unit,
) {
    Row(modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)) {
        AvailabilityCalendarTopHeaderButton(
            icon = Res.drawable.ic_calendar_left,
            onClick = availabilityMonthMinusOne,
        )
        AvailabilityCalendarTopHeaderMainText(availability.currentMonth)
        AvailabilityCalendarTopHeaderButton(
            icon = Res.drawable.ic_calendar_right,
            onClick = availabilityMonthPlusOne
        )
    }
}

@Composable
private fun AvailabilityCalendarTopHeaderButton(
    icon: DrawableResource,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.padding(vertical = 6.dp),
        onClick = onClick,
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = "",
        )
    }
}

@Composable
private fun RowScope.AvailabilityCalendarTopHeaderMainText(currentMonth: String) {
    Text(
        modifier = Modifier
            .weight(1F)
            .align(Alignment.CenterVertically),
        text = currentMonth,
        style = h3Medium,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun AvailabilityCalendarMainContent(
    availability: ProAvailabilityState,
    isSelectedDay: @Composable (Int) -> Boolean,
    onDateClicked: (Int) -> Unit,
) {
    Row(Modifier.padding(horizontal = 8.dp)) {
        for (dayIndex in 0..<7) {
            DayColumn(dayIndex, availability, isSelectedDay, onDateClicked)
        }
    }
}

@Composable
private fun RowScope.DayColumn(
    dayIndex: Int,
    availability: ProAvailabilityState,
    isSelectedDay: @Composable (Int) -> Boolean,
    onDateClicked: (Int) -> Unit,
) {
    Column(modifier = Modifier.weight(1F)) {
        DayText(dayIndex)
        DayDivider()
        for (weekIndex in 0..<availability.perWeek) {
            Day(availability, dayIndex, weekIndex, isSelectedDay, onDateClicked)
        }
    }
}


@Composable
private fun DayText(dayIndex: Int) {
    val text = remember(dayIndex) { requireNotNull(weekNumberMap[dayIndex]) }
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = text,
        textAlign = TextAlign.Center,
        style = body1Medium,
    )
}

@Composable
private fun DayDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(top = 8.dp, bottom = 20.dp),
        thickness = 0.5.dp,
        color = MaterialTheme.colorScheme.errorContainer
    )
}

@Composable
private fun Day(
    availability: ProAvailabilityState,
    dayIndex: Int,
    weekIndex: Int,
    isSelectedDay: @Composable (Int) -> Boolean,
    onDateClicked: (Int) -> Unit,
) {
    val lastDay = availability.lengthOfMonth
    val firstDay = availability.firstDay
    val day = (dayIndex + 1) + (weekIndex * 7) - firstDay
    val isEmptyDay = day <= 0 || day > lastDay

    if (isEmptyDay) {
        EmptyDay()
    } else if (isSelectedDay(day)) {
        SelectedDay(day, onDateClicked)
    } else {
        NormalDay(day, onDateClicked)
    }
}

@Composable
private fun EmptyDay() {
    Box(modifier = Modifier.aspectRatio(1F))
}

@Composable
private fun NormalDay(day: Int, onDateClicked: (Int) -> Unit) {
    CommonDay(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.errorContainer,
                shape = CircleShape,
            )
            .clickable { onDateClicked(day) },
        text = day.toString(),
        textColor = MaterialTheme.colorScheme.errorContainer,
    )
}

@Composable
private fun SelectedDay(day: Int, onDateClicked: (Int) -> Unit) {
    val backgroundColor = MaterialTheme.colorScheme.outlineVariant
    CommonDay(
        modifier = Modifier.drawBehind { drawCircle(backgroundColor) }
            .testTag("SelectedDay")
            .clickable { onDateClicked(day) },
        text = day.toString(),
        textColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
private fun CommonDay(
    modifier: Modifier,
    text: String,
    textColor: Color,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1F)
            .padding(4.dp)
            .then(modifier)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = body1,
            color = textColor
        )
    }
}
